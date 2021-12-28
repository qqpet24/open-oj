package com.xmu.problem.controller;

import com.xmu.problem.service.ProblemListService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/list")
public class ProblemListController {

    @Autowired
    private ProblemListService problemListService;

    @GetMapping("/all")
    public Object getProblemList(){
        return problemListService.getProblemList();
    }

    @GetMapping("/{id}")
    @ApiOperation("获取题单基础信息")
    public Object getListInfo(@PathVariable Long id){
        return null;
    }

    @PostMapping
    @ApiOperation("创建或修改一个题单")
    public Object createOrModifyProblemList(){
        return null;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个题单")
    public Object deleteProblemList(@PathVariable Long id){
        return null;
    }

    @GetMapping("/{id}/problems")
    @ApiOperation("获取题单中的所有题目")
    public Object getProblemsByList(@PathVariable Long id){
        return null;
    }

}
