package com.xmu.problem.controller;

import cn.hutool.json.JSONUtil;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DockerClientBuilder;
import com.xmu.auth.service.UserService;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.request.JudgeDTO;
import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.ProblemService;
import com.xmu.problem.util.JudgeSystem;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ApiOperation("获取题目详情")
    public Object getProblem(@PathVariable Long id) {
        return problemService.getProblem(id);
    }

    @PostMapping
    @ApiOperation("创建或修改题目")
    public Object createOrModifyProblem(@RequestBody ProblemDTO problem) {
        return problemService.createOrModifyProblem(problem);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除题目")
    public Object deleteProblem(@PathVariable Long id) {
        return problemService.deleteProblem(id);
    }

    @GetMapping("/all")
    @ApiOperation("获取全部题目")
    public Object getProblems() {
        return problemService.getProblems();
    }

    @PostMapping("{id}/judge")
    @ApiOperation("判题")
    public Object judgeProblem(@PathVariable Long id, @RequestBody JudgeDTO judgeDTO) throws Exception {

        Problem problem = problemService.getById(id);//9999
        //KB
        Integer memoryLimit = problem.getMemoryLimit();
        //ms
        Integer timeLimit = problem.getTimeLimit();
        Long userId = judgeDTO.getUserId();//7788
        //主控建立文件夹 ~/user/7788/code/9999
        String codeFilePath = judgeDTO.getWorkPath()+"/user/" + userId + "/code/" + id;
        File codeFile = new File(codeFilePath);
        //主控建立文件夹 ~/user/7788/solution/9999
        String solutionFilePath = judgeDTO.getWorkPath()+"/user/"+ userId + "/solution/" + id;
        File solutionFile = new File(solutionFilePath);
        codeFile.mkdirs();
        solutionFile.mkdirs();

        ////文件夹挂载到判题机
        //DockerClient dockerClient = DockerClientBuilder
        //        .getInstance("tcp://localhost:2275").build();
        //// 获取服务器信息
        //Info info = dockerClient.infoCmd().exec();
        //String infoStr = JSONUtil.toJsonStr(info);
        //System.out.println(infoStr);
        //CreateContainerResponse container1
        //        = dockerClient.createContainerCmd("jdk11-centos:1.0")
        //        //B
        //        .withCmd("/bin/bash", judgeDTO.getCmd())
        //        .withMemory(memoryLimit*1024L+50*1024*1024L)
        //        .withVolumes()
        //        .withBinds(new Bind(codeFilePath,new Volume(codeFilePath)),new Bind(solutionFilePath,new Volume(solutionFilePath)))
        //        .withName("hangge_http_server") //给容器命名
        //        .withPortBindings(PortBinding.parse("7999:80")) //Apache端口是80，映射到主机的8080端口
        //        .exec();
        //dockerClient.startContainerCmd(container1.getId()).exec();
        //return infoStr;
        //判题机 读取代码文件->编译、运行用户程序(拦截一些不安全的系统调用或者~~不给写入权限~~) -> 判题 -> 写入结果
        String code = judgeDTO.getCode();
        FileWriter writer;
        try {
            writer = new FileWriter(codeFilePath + "/Main.java");
            writer.write("");//清空原文件内容
            writer.write(code);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JavaCompiler.compile(codeFilePath);
        byte[] execute = JavaCompiler.execute(new File(judgeDTO.getWorkPath()+"\\problem\\" + id + "\\1.in"));
        System.out.println(Arrays.toString(execute));
        //正确：

        //常规的错误:时间超限、内存超限、输出超限、答案错误、编译错误（会在特定的时间点返回结果）

        //非常规的错误：运行错误(想办法知道docker什么时候崩溃的)

        //docker
        //docker里边的服务
        //用户程序

        //主控读取 判题结果文件

        //~~判题机调用 /solution/receive~~
        return null;
    }

    @PostMapping("/{id}/receive/user/{userId}")
    @ApiOperation("接收判题结果")
    public Object receiveSolution(@PathVariable Long id, @PathVariable Long userId) {
        return null;
    }
}
