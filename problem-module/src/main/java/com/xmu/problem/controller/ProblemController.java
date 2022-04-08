package com.xmu.problem.controller;

import com.xmu.auth.service.UserService;
import com.xmu.problem.request.JudgeDTO;
import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.impl.JudgeService;
import com.xmu.problem.service.ProblemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public Object judgeProblem(@PathVariable Long id, @RequestBody JudgeDTO judgeDTO, HttpServletRequest request) throws Exception {
        //return problemService.judge(id,judgeDTO,request);
        return problemService.judgev2(id,judgeDTO,request);
    }

    @PostMapping("/{id}/receive/user/{userId}")
    @ApiOperation("接收判题结果")
    public Object receiveSolution(@PathVariable Long id, @PathVariable Long userId) {
        return null;
    }

    @GetMapping("/{id}/basicInfo")
    @ApiOperation("获取题目基础信息")
    public Object getBasicProblemInfo(@PathVariable Long id){
        return problemService.getBasicProblemInfo(id);
    }

    @GetMapping("/{id}/case")
    @ApiOperation("获取题目测试用例名称")
    public Object getTestCaseNameById(@PathVariable Long id){
        return problemService.getTestCaseNameById(id);
    }
}
