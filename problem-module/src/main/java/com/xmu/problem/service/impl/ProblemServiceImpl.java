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
import com.xmu.problem.reponse.ProblemBriefDTO;
import com.xmu.problem.request.JudgeDTO;
import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.CompileInfoService;
import com.xmu.problem.service.ProblemService;
import com.xmu.problem.service.SolutionService;
import com.xmu.problem.service.SourceCodeService;
import com.xmu.problem.util.LanguageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
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
    public void judge(Long id, JudgeDTO judgeDTO, HttpServletRequest request) {

        //1.获取基础信息
        String ipAddress = IpUtil.getIpAddress(request);
        String code = judgeDTO.getCode();
        Long userId = judgeDTO.getUserId();
        String language = judgeDTO.getLanguage();   //"Java"
        LanguageInfo languageInfo
                = LanguageInfo.getInfoByLanguage(language);

        //插入一条solution记录
        Solution solution = Solution.of(
                null,
                id,
                userId,
                null,
                null,
                LocalDateTime.now(),
                null,
                Objects.requireNonNull(languageInfo).getCode(),
                ipAddress,
                null,
                null,
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

        //编译（async）
        String compileError;
        try {
            compileError = compile(languageInfo, workDir).get();
            if (compileError != null) {
                boolean compileErrorSaved = compileInfoService.save(CompileInfo.of(
                        null,
                        solutionId,
                        compileError
                ));
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //获取.in和.out文件

        //执行 && 统计pass_rate

        //插入runtimeInfo

        //返回结果（给前端）

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

    @Async("taskExecutor")
    public AsyncResult<String> compile(LanguageInfo languageInfo, String workDir) {

        try {
            Process process = Runtime.getRuntime().exec(
                    languageInfo.compileCmd(),
                    null,
                    new File(workDir));
            if (process.waitFor(5, TimeUnit.SECONDS)) {
                process.destroy();
                return new AsyncResult<>(null);
            }
            //如果编译成功，则不会有报错，而是直接生成.class文件，如果编译失败，则errorStream会有报错信息
            InputStream errorStream = process.getErrorStream();
            return new AsyncResult<>(msgFromError(errorStream));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(null);

    }

    private String msgFromError(final InputStream input) {
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new InputStreamReader(input, "GB2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder msg = new StringBuilder();
        String line;
        try {
            while ((line = bf.readLine()) != null) {
                msg.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg.toString();
    }


    //未完成
    @Async
    public AsyncResult<Object> execute(LanguageInfo languageInfo, String workDir, String inCasePath,int timeLimit){
        ProcessBuilder processBuilder
                = new ProcessBuilder(languageInfo.executeCmd());
        processBuilder.directory(new File(workDir));
        processBuilder.redirectInput(new File(inCasePath));
        try {
            Process process = processBuilder.start();
            if(!process.waitFor(timeLimit,TimeUnit.SECONDS)){
                process.destroy();
                return new AsyncResult<>("超时了！！！");
            }
            InputStream errorStream ;
            //如果发生错误
            if((errorStream= process.getErrorStream())!=null){
                return new AsyncResult<>(msgFromError(errorStream));
                //没有发生错误
            }else {
                return new AsyncResult<>(process.getInputStream());
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

}
