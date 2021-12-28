package com.xmu.problem.controller;

import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.ProblemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping("/{id}")
    @ApiOperation("获取题目详情")
    public Object getProblem(@PathVariable Long id){
        return problemService.getProblem(id);
    }

    @PostMapping
    @ApiOperation("创建或修改题目")
    public Object createOrModifyProblem(@RequestBody ProblemDTO problem){
        return problemService.createOrModifyProblem(problem);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("删除题目")
    public Object deleteProblem(@PathVariable Long id){
        return problemService.deleteProblem(id);
    }

    @GetMapping("/all")
    @ApiOperation("获取全部题目")
    public Object getProblems(){
        return problemService.getProblems();
    }

    @PostMapping("{id}/judge")
    @ApiOperation("判题")
    public Object judgeProblem(@PathVariable Long id){
        return null;
    }
}
