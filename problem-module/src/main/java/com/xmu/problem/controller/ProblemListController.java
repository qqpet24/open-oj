package com.xmu.problem.controller;

import com.xmu.problem.request.ProblemListDTO;
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
        return problemListService.getListInfo(id);
    }

    @PostMapping
    @ApiOperation("创建或修改一个题单")
    public Object createOrModifyProblemList(@RequestBody ProblemListDTO problemListDTO){
        return problemListService.createOrModifyProblemList(problemListDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个题单")
    public Object deleteProblemList(@PathVariable Long id){
        return problemListService.deleteProblemList(id);
    }
}
