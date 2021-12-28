package com.xmu.problem.controller;

import com.xmu.problem.service.ContestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/contest")
public class ContestController {

    @Autowired
    private ContestService contestService;

    @GetMapping("/all")
    @ApiOperation("获取全部竞赛")
    public Object getContests(){
        return contestService.getContests();
    }

    @GetMapping("/contest/{id}")
    @ApiOperation("获取竞赛基础信息")
    public Object getContestInfo(@PathVariable Long id){
        return contestService.getContestInfo(id);
    }

    @PostMapping("/contest")
    @ApiOperation("创建或修改一个竞赛")
    public Object createOrModifyProblemToList(){
        return null;
    }

    @DeleteMapping("/contest/{id}}")
    @ApiOperation("删除某个竞赛")
    public Object deleteContest(@PathVariable Long id){
        return contestService.deleteContest(id);
    }

    @GetMapping("/contest/{id}/problems")
    @ApiOperation("获取竞赛中的所有题目")
    public Object getProblemsFromContest(@PathVariable Long id){
        return null;
    }

    @PostMapping("/contest/{id}")
    @ApiOperation("为竞赛新增题目")
    public Object addProblemToContest(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/{problemId}/list/{contestId}/")
    @ApiOperation("删除竞赛下的某个题目")
    public Object deleteProblemFromContest(@PathVariable Long problemId,@PathVariable Long contestId){
        return null;
    }
}
