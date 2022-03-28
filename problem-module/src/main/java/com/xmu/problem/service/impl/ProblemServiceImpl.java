package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.IpUtil;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.CompileInfo;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.domain.Solution;
import com.xmu.problem.domain.SourceCode;
import com.xmu.problem.mapper.ProblemMapper;
import com.xmu.problem.reponse.JudgeResultDTO;
import com.xmu.problem.reponse.JudgeReturnInfo;
import com.xmu.problem.reponse.ProblemBriefDTO;
import com.xmu.problem.request.JudgeDTO;
import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.CompileInfoService;
import com.xmu.problem.service.ProblemService;
import com.xmu.problem.service.SolutionService;
import com.xmu.problem.service.SourceCodeService;
import com.xmu.problem.request.util.JudgeStatus;
import com.xmu.problem.request.util.LanguageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Autowired
    private SolutionService solutionService;

    @Autowired
    private SourceCodeService sourceCodeService;

    @Autowired
    private CompileInfoService compileInfoService;

    @Override
    public Object getProblems() {
        List<ProblemBriefDTO> problemBriefs
                = this.list(Wrappers.lambdaQuery()).stream().map(Problem::brief).collect(Collectors.toList());
        return problemBriefs.isEmpty() ? Response.of(ResponseCode.NOT_FOUND) : Response.of(ResponseCode.OK, problemBriefs);
    }

    @Override
    public Object getProblem(Long id) {
        Problem problem = this.getById(id);
        return problem != null ? Response.of(ResponseCode.OK, problem) : Response.of(ResponseCode.PROBLEM_NOT_EXIST);
    }

    @Override
    public Object deleteProblem(Long id) {
        boolean result = this.removeById(id);
        return result ? Response.of(ResponseCode.OK) : Response.of(ResponseCode.PROBLEM_NOT_EXIST);
    }

    @Override
    public Object createOrModifyProblem(ProblemDTO problem) {
        Problem problem1 = new Problem(problem);
        problem.setInDate(LocalDateTime.now());
        return this.saveOrUpdate(problem1) ? Response.of(ResponseCode.OK) : Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * @param id       problem id
     * @param judgeDTO judge Basic Info
     */
    @Override
    public Object judge(Long id, JudgeDTO judgeDTO, HttpServletRequest request) throws Exception {

        //1.获取基础信息
        String ipAddress = IpUtil.getIpAddress(request);
        String code = judgeDTO.getCode();
        Long userId = judgeDTO.getUserId();
        String language = judgeDTO.getLanguage();   //"Java"
        LanguageInfo languageInfo
                = LanguageInfo.getInfoByLanguage(language);
        Problem problem = this.getById(id);
        //未完成，必做，问题存在检查
        if (problem == null) {
            throw new Exception("Problem no found");
        }
        if (language == null || languageInfo == null) {
            throw new Exception("Language no support");
        }
        if (userId == null) {
            throw new Exception("User no found");
        }
        if (judgeDTO.getCode() == null) {
            throw new Exception("Code no found");
        }

        //插入一条solution记录
        Solution solution = Solution.of(
                null,
                id,
                userId,
                null,
                null,
                LocalDateTime.now(),
                null,
                Objects.requireNonNull(languageInfo).getLanguage(),
                ipAddress,
                null,
                judgeDTO.getCode().length(),
                null,
                0.0
        );
        boolean solutionSaved = solutionService.save(solution);

        //插入sourceCode记录
        Long solutionId = solution.getId();
        boolean codeSaved = sourceCodeService.save(SourceCode.of(
                null,
                solutionId,
                code
        ));

        if (!solutionSaved || !codeSaved) {
            throw new Exception("Database error");
        }

        //创建代码文件并写入代码
        String workDir = String.format(
                "/user/%d/problem/%d/submit/%d/",
                userId,
                id,
                solutionId
        );
        //  /user/99/problem/100/submit/56/Main.java
        String filePath = String.format("%s%s", workDir, languageInfo.getNameBeforeCompile());
        boolean codeFileCreated = createNewFile(code, filePath);

        if (!codeFileCreated) {
            throw new Exception("Filesystem error");
        }

        //编译（async）
        JudgeReturnInfo judgeReturnInfo = compile(languageInfo, workDir).get();
        assert judgeReturnInfo != null;
        if (!judgeReturnInfo.getStatus().equals(JudgeStatus.COMPILE_SUCCESS.getType())) {
            boolean compileErrorSaved = compileInfoService.save(CompileInfo.of(
                    null,
                    solutionId,
                    judgeReturnInfo.getErrorInfo()
            ));
            solution.setResult(judgeReturnInfo.getStatus());
            solutionService.updateById(solution);
            return JudgeResultDTO.of(
                    solutionId,
                    judgeReturnInfo.getStatus(),
                    null,
                    judgeReturnInfo.getErrorInfo()
            );
        }

        //获取.in和.out文件
        String inAndOutDir = String.format(
                "/problem/%d/testData/",
                id
        );
        List<String> testFileOriginalNameList = getTestFileName(inAndOutDir);

        //执行 比较 统计pass_rate
        JudgeResultDTO judgeResultDTO = JudgeResultDTO.of(
                solutionId,
                null,
                new LinkedList<>(),
                null
        );
        for (String testFileOriginalName : testFileOriginalNameList) {
            try {
                judgeReturnInfo = execute(languageInfo, workDir, inAndOutDir + testFileOriginalName + ".in", inAndOutDir + testFileOriginalName + ".out", problem.getTimeLimit(), problem.getMemoryLimit(), testFileOriginalName).get();
                judgeReturnInfo.setTestCase(testFileOriginalName);
                judgeResultDTO.getExecuteInfo().add(judgeReturnInfo);
            } catch (ExecutionException e) {
                e.printStackTrace();
                solution.setResult(JudgeStatus.UNKNOWN_ERROR.getType());
                solutionService.updateById(solution);
                judgeResultDTO.getExecuteInfo().add(JudgeReturnInfo.of(
                        JudgeStatus.UNKNOWN_ERROR.getType(),
                        testFileOriginalName,
                        null,
                        null,
                        null
                ));
            }
        }
        //delete file before return
        deleteFile(workDir + languageInfo.getNameBeforeExecute());

        //update solution table
        {
            List<JudgeReturnInfo> list = judgeResultDTO.getExecuteInfo();
            Integer time = 0;
            Integer memory = 0;
            String error = null;
            int passCase = 0;
            int failCase = 0;

            for(JudgeReturnInfo tmp:list){
                if(!tmp.getStatus().equals("AC")){
                    if(error == null) error = tmp.getStatus();
                    failCase++;
                }else{
                    time+=tmp.getExecuteTime()==null?0:(int)(long)tmp.getExecuteTime();
                    memory+=tmp.getExecuteMem()==null?0:(int)(long)tmp.getExecuteMem();
                    passCase++;
                }
            }

            //无测试用例时候，后台记录AC,但是不返回
            if(passCase+failCase==0){
                solution.setResult(JudgeStatus.ACCEPTED.getType());
                solution.setTime(0);
                solution.setMemory(0);
                solution.setPass_rate(1.0d);
            }else{
                double passRate = (double)passCase/((double)passCase+(double)failCase);
                if(error!=null){
                    solution.setResult(error);
                }else{
                    solution.setResult(JudgeStatus.ACCEPTED.getType());
                    solution.setTime(time);
                    solution.setMemory(memory);
                }
                solution.setPass_rate(passRate);
            }

            solutionService.updateById(solution);
        }
        return judgeResultDTO;
        //插入runtimeInfo

        //返回结果（给前端）
    }

    @Override
    public Object getBasicProblemInfo(Long id) {
        Problem problem = this.getById(id);
        return Response.of(ResponseCode.OK, problem.problemBasicInfo());
    }

    //in有几个就返回几个,in必须有对应out,否则不返回  test.in test.out test1.in test1.out test2.in
    private List<String> getTestFileName(String path) {
        File testFile = new File(path);
        File[] allFiles = testFile.listFiles();
        if (allFiles == null) {
            return new LinkedList<>();
        }
        Set<String> inFileSet = new HashSet<>();
        Set<String> outFileSet = new HashSet<>();
        for (File file : allFiles) {
            String fileName = file.getName();
            int lastIndex = fileName.lastIndexOf(".");
            String fileOriginalName = fileName.substring(0, lastIndex);
            String fileExtensionName = fileName.substring(lastIndex + 1, fileName.length());
            if (fileExtensionName.equals("in")) {
                inFileSet.add(fileOriginalName);
            } else if (fileExtensionName.equals("out")) {
                outFileSet.add(fileOriginalName);
            }
        }
        inFileSet.retainAll(outFileSet);
        return new LinkedList<>(inFileSet);
    }

    private boolean createNewFile(String code, String path) {
        File file = new File(path);

        //新建父目录
        boolean parentDirCreated = false;
        if (!file.getParentFile().exists()) {
            parentDirCreated = file.getParentFile().mkdirs();
        }

        //新建文件
        boolean created = false;
        if (!file.exists()) {
            try {
                created = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //写入code
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8)) {
            bufferedWriter.write(code);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parentDirCreated && created;
    }

    private boolean deleteFile(String path) {
        try {
            File file = new File(path);
            return file.delete();
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    @Async("taskExecutor")
    public AsyncResult<JudgeReturnInfo> compile(LanguageInfo languageInfo, String workDir) {
        JudgeReturnInfo judgeReturnInfo = new JudgeReturnInfo();
        try {
            Process process = Runtime.getRuntime().exec(
                    languageInfo.compileCmd(),
                    null,
                    new File(workDir));

            //magic value
            Long MAX_COMPILE_TIME = 5L;
            if (!process.waitFor(MAX_COMPILE_TIME, TimeUnit.SECONDS)) {
                if (process.isAlive()) {
                    process.destroy();
                    judgeReturnInfo.setStatus(JudgeStatus.COMPILE_TIME_LIMIT_EXCEED.getType());
                    judgeReturnInfo.setErrorInfo("Compile exceed " + MAX_COMPILE_TIME + " second(s)");
                    return new AsyncResult<>(judgeReturnInfo);
                }
            }

            //如果编译成功，则不会有报错，而是直接生成.class文件，如果编译失败，则errorStream会有报错信息
            String errorInfo = msgFromError(process.getErrorStream());
            if (errorInfo != null) {
                judgeReturnInfo.setStatus(JudgeStatus.COMPILE_ERROR.getType());
                judgeReturnInfo.setErrorInfo(errorInfo);
                return new AsyncResult<>(judgeReturnInfo);
            }
        } catch (Exception e) {
            judgeReturnInfo.setStatus(JudgeStatus.UNKNOWN_ERROR.getType());
            e.printStackTrace();
        }
        judgeReturnInfo.setStatus(JudgeStatus.COMPILE_SUCCESS.getType());
        return new AsyncResult<>(judgeReturnInfo);
    }

    private String msgFromError(final InputStream input) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(input, "GB2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder msg = new StringBuilder();
        try {
            Boolean isFirst = true;
            while (true) {
                String line = bf.readLine();
                if (isFirst) {
                    isFirst = false;
                    if (line == null) {
                        return null;
                    }
                }
                if (line == null) {
                    break;
                }
                //magic value
                //max return error info <= 256kb
                if (msg.length() + line.length() >= 256 * 1024) {
                    break;
                }
                msg.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }

    private long[] readJudgeResult(String path) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str = in.readLine();
            String[] strs = str.split(" ");
            long[] result = new long[strs.length];
            for (int i = 0; i < strs.length; i++) {
                result[i] = Long.parseLong(strs[i]);
            }
            return result;
        } catch (IOException e) {
        }
        return null;
    }

    @Async("taskExecutor")
    public AsyncResult<JudgeReturnInfo> execute(LanguageInfo languageInfo, String workDir, String inCasePath, String standardOutPath, double timeLimit, int memoryLimit, String originalName) {
        JudgeReturnInfo judgeReturnInfo = new JudgeReturnInfo();
        ProcessBuilder processBuilder = new ProcessBuilder(languageInfo.executeCmd());
        processBuilder.directory(new File(workDir));
        processBuilder.redirectInput(new File(inCasePath));
        Map<String, String> map = processBuilder.environment();
        //magic value
        map.put("LD_PRELOAD","/tools/fakev2.so");

        String cJudgeResultPath = workDir + originalName + ".judge";

        try {
            Long beforeExecuteTime = System.currentTimeMillis();
            Process process = processBuilder.start();
            //magic value
            ProcessBuilder cJudgeProcessBuilder = new ProcessBuilder(List.of("/tools/mem", process.pid() + "", memoryLimit + "", cJudgeResultPath));
            Process cJudgeProcess = cJudgeProcessBuilder.start();
            if (!process.waitFor(languageInfo.getRealTimeLimit(timeLimit).longValue(), TimeUnit.MILLISECONDS)) {
                if (process.isAlive()) {
                    process.destroy();
                    judgeReturnInfo.setExecuteTime(System.currentTimeMillis() - beforeExecuteTime);
                    judgeReturnInfo.setStatus(JudgeStatus.EXECUTION_TIME_LIMIT_EXCEED.getType());
                    return new AsyncResult<>(judgeReturnInfo);
                }
            }

            Long realExecuteTime = System.currentTimeMillis() - beforeExecuteTime;
            judgeReturnInfo.setExecuteTime(realExecuteTime);
            long[] cJudgeResult = {0};
            if (cJudgeProcess.isAlive()) {
                //magic value
                if (cJudgeProcess.waitFor(500, TimeUnit.MILLISECONDS)) {
                    if (cJudgeProcess.isAlive()) {
                        cJudgeProcess.destroy();
                    } else {
                        long[] tmp = readJudgeResult(cJudgeResultPath);
                        if (tmp != null) cJudgeResult = tmp;
                    }
                }
            } else {
                long[] tmp = readJudgeResult(cJudgeResultPath);
                if (tmp != null) cJudgeResult = tmp;
            }
            judgeReturnInfo.setExecuteMem(cJudgeResult[0]);

            if (cJudgeResult[0] > memoryLimit) {
                judgeReturnInfo.setStatus(JudgeStatus.MEMORY_LIMIT_EXCEED.getType());
                return new AsyncResult<>(judgeReturnInfo);
            }

            if (process.getInputStream() != null) {
                boolean result = streamCompare(process.getInputStream(), new FileInputStream(standardOutPath));
                if (!result) {
                    //比如一直开内存导致程序被Kill，并没有错误流
                    if (process.exitValue() != 0) {
                        judgeReturnInfo.setStatus(JudgeStatus.RUNTIME_ERROR.getType());
                        judgeReturnInfo.setErrorInfo("Process exit with non zero value:" + process.exitValue() + " and the answer is incorrect");
                    } else {
                        judgeReturnInfo.setStatus(JudgeStatus.WRONG_ANSWER.getType());
                    }
                } else {
                    judgeReturnInfo.setStatus(JudgeStatus.ACCEPTED.getType());
                }
                return new AsyncResult<>(judgeReturnInfo);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        judgeReturnInfo.setStatus(JudgeStatus.UNKNOWN_ERROR.getType());
        return new AsyncResult<>(judgeReturnInfo);
    }

    public boolean streamCompare(InputStream stream1, InputStream stream2) throws IOException {
        //magic value
        //读取缓冲区大小 256k;
        final int BUFFER_SIZE = 262144;
        byte[] buffer1 = new byte[BUFFER_SIZE];
        byte[] buffer2 = new byte[BUFFER_SIZE];
        while (true) {
            int l1 = stream1.readNBytes(buffer1, 0, BUFFER_SIZE);
            int l2 = stream2.readNBytes(buffer2, 0, BUFFER_SIZE);
            if (l1 != l2) {
                return false;
            }
            for (int i = 0; i < l1; i++) {
                if (buffer1[i] != buffer2[i]) {
                    return false;
                }
            }
            if (l1 < BUFFER_SIZE) {
                break;
            }
        }
        return true;
    }

    @Override
    public Object judgev2(Long id, JudgeDTO judgeDTO, HttpServletRequest request) throws Exception{
        //本函数调用新版的C语言判题机,性能更加高效
        //1.获取基础信息
        String ipAddress = IpUtil.getIpAddress(request);
        String code = judgeDTO.getCode();
        Long userId = judgeDTO.getUserId();
        String language = judgeDTO.getLanguage();   //"Java"
        LanguageInfo languageInfo
                = LanguageInfo.getInfoByLanguage(language);
        Problem problem = this.getById(id);
        //未完成，必做，问题存在检查
        if (problem == null) {
            throw new Exception("Problem no found");
        }
        if (language == null || languageInfo == null) {
            throw new Exception("Language no support");
        }
        if (userId == null) {
            throw new Exception("User no found");
        }
        if (judgeDTO.getCode() == null) {
            throw new Exception("Code no found");
        }

        //插入一条solution记录
        Solution solution = Solution.of(
                null,
                id,
                userId,
                null,
                null,
                LocalDateTime.now(),
                null,
                Objects.requireNonNull(languageInfo).getLanguage(),
                ipAddress,
                null,
                judgeDTO.getCode().length(),
                null,
                0.0
        );
        boolean solutionSaved = solutionService.save(solution);

        //插入sourceCode记录
        Long solutionId = solution.getId();
        boolean codeSaved = sourceCodeService.save(SourceCode.of(
                null,
                solutionId,
                code
        ));

        if (!solutionSaved || !codeSaved) {
            throw new Exception("Database error");
        }

        //创建代码文件并写入代码
        String workDir = String.format(
                "/user/%d/problem/%d/submit/%d/",
                userId,
                id,
                solutionId
        );
        //  /user/99/problem/100/submit/56/Main.java
        String filePath = String.format("%s%s", workDir, languageInfo.getNameBeforeCompile());
        boolean codeFileCreated = createNewFile(code, filePath);

        if (!codeFileCreated) {
            throw new Exception("Filesystem error");
        }

        //编译（async）
        JudgeReturnInfo judgeReturnInfo = compile(languageInfo, workDir).get();
        assert judgeReturnInfo != null;
        if (!judgeReturnInfo.getStatus().equals(JudgeStatus.COMPILE_SUCCESS.getType())) {
            boolean compileErrorSaved = compileInfoService.save(CompileInfo.of(
                    null,
                    solutionId,
                    judgeReturnInfo.getErrorInfo()
            ));
            solution.setResult(judgeReturnInfo.getStatus());
            solutionService.updateById(solution);
            return JudgeResultDTO.of(
                    solutionId,
                    judgeReturnInfo.getStatus(),
                    null,
                    judgeReturnInfo.getErrorInfo()
            );
        }

        //获取.in和.out文件
        String inAndOutDir = String.format(
                "/problem/%d/testData/",
                id
        );
        List<String> testFileOriginalNameList = getTestFileName(inAndOutDir);

        //执行 比较 统计pass_rate
        JudgeResultDTO judgeResultDTO = JudgeResultDTO.of(
                solutionId,
                null,
                new LinkedList<>(),
                null
        );

//        LinkedList<AsyncResult<JudgeReturnInfo>> asyncList = new LinkedList<>();
//        for (String testFileOriginalName : testFileOriginalNameList) {
//            try {
//                judgeReturnInfo = executev2(languageInfo, workDir, inAndOutDir, testFileOriginalName,  problem.getTimeLimit(), problem.getMemoryLimit()).get();
//                judgeResultDTO.getExecuteInfo().add(judgeReturnInfo);
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//                solution.setResult(JudgeStatus.UNKNOWN_ERROR.getType());
//                solutionService.updateById(solution);
//                judgeResultDTO.getExecuteInfo().add(JudgeReturnInfo.of(
//                        JudgeStatus.UNKNOWN_ERROR.getType(),
//                        testFileOriginalName,
//                        null,
//                        null,
//                        null
//                ));
//            }
//        }

        LinkedList<Future<JudgeReturnInfo>> asyncList = new LinkedList<>();
        for (String testFileOriginalName : testFileOriginalNameList) {
            try {
                Future<JudgeReturnInfo> judgeReturnInfoAsyncResult = executev2(languageInfo, workDir, inAndOutDir, testFileOriginalName,  problem.getTimeLimit(), problem.getMemoryLimit());
                asyncList.add(judgeReturnInfoAsyncResult);
            } catch (Exception e) {
                e.printStackTrace();
                solution.setResult(JudgeStatus.UNKNOWN_ERROR.getType());
                solutionService.updateById(solution);
                judgeResultDTO.getExecuteInfo().add(JudgeReturnInfo.of(
                        JudgeStatus.UNKNOWN_ERROR.getType(),
                        testFileOriginalName,
                        null,
                        null,
                        null
                ));
            }
        }

        for(Future<JudgeReturnInfo> judgeReturnInfoAsyncResult:asyncList){
            judgeReturnInfo = judgeReturnInfoAsyncResult.get();
            judgeResultDTO.getExecuteInfo().add(judgeReturnInfo);
        }

        //update solution table
        {
            List<JudgeReturnInfo> list = judgeResultDTO.getExecuteInfo();
            Integer time = 0;
            Integer memory = 0;
            String error = null;
            int passCase = 0;
            int failCase = 0;

            for(JudgeReturnInfo tmp:list){
                if(!tmp.getStatus().equals("AC")){
                    if(error == null) error = tmp.getStatus();
                    failCase++;
                }else{
                    time+=tmp.getExecuteTime()==null?0:(int)(long)tmp.getExecuteTime();
                    memory+=tmp.getExecuteMem()==null?0:(int)(long)tmp.getExecuteMem();
                    passCase++;
                }
            }

            //无测试用例时候，后台记录AC,但是不返回
            if(passCase+failCase==0){
                solution.setResult(JudgeStatus.ACCEPTED.getType());
                solution.setTime(0);
                solution.setMemory(0);
                solution.setPass_rate(1.0d);
            }else{
                double passRate = (double)passCase/((double)passCase+(double)failCase);
                if(error!=null){
                    solution.setResult(error);
                }else{
                    solution.setResult(JudgeStatus.ACCEPTED.getType());
                    solution.setTime(time);
                    solution.setMemory(memory);
                }
                solution.setPass_rate(passRate);
            }

            solutionService.updateById(solution);
        }
        return judgeResultDTO;
    }

    private JudgeReturnInfo readJudgeResultv2(String path) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str = in.readLine();
        String[] strs = str.split(" ");
        HashMap<String,String> map = new HashMap<>();

        for (String tmp : strs) {
            map.put(tmp.split(":")[0],tmp.split(":")[1]);
        }
        JudgeReturnInfo judgeReturnInfo = new JudgeReturnInfo();
        judgeReturnInfo.setStatus(map.getOrDefault("flag",null));
        judgeReturnInfo.setExecuteTime(Long.parseLong(map.getOrDefault("ms",null)));
        judgeReturnInfo.setExecuteMem(Long.parseLong(map.getOrDefault("mb",null)));
        return judgeReturnInfo;
    }

    @Async("taskExecutor")
    public AsyncResult<JudgeReturnInfo> executev2(LanguageInfo languageInfo,String workDir, String inAndOutDir,String originInFileName, double timeLimit, int memoryLimit) {
        String standardInputFile = inAndOutDir+originInFileName+".in";
        String standardOutputFIle = inAndOutDir+originInFileName+".out";
        String userOutPutFile = workDir+originInFileName+".out";
        String userErrorFile = workDir+originInFileName+".error";
        String judgeResult = workDir+originInFileName+".result";
        //magic value,MB
        int maxFileSize = 128;

        JudgeReturnInfo judgeReturnInfo = new JudgeReturnInfo();
        judgeReturnInfo.setStatus("UE");

        try{
            ProcessBuilder cJudgeProcessBuilder = new ProcessBuilder(List.of("/tools/judgev2-cmd",((int) timeLimit)+"",memoryLimit+"",maxFileSize+"",standardInputFile,standardOutputFIle,userOutPutFile,userErrorFile,workDir,languageInfo.getCode()+"",judgeResult));
            Process cJudgeProcess = cJudgeProcessBuilder.start();
            if (cJudgeProcess.waitFor((((int)timeLimit)/1000+(((int)timeLimit)%1000>0?1:0))*2, TimeUnit.SECONDS)) {
                if (cJudgeProcess.isAlive()) {
                    cJudgeProcess.destroy();
                } else {
                    judgeReturnInfo = readJudgeResultv2(judgeResult);
                }
            }
        }catch (Exception e){

        }
        judgeReturnInfo.setTestCase(originInFileName);
        return new AsyncResult<>(judgeReturnInfo);
    }
}
