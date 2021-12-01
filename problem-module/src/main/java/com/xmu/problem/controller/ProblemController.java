package com.xmu.problem.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/problem")
public class ProblemController {

    @GetMapping("/{id}")
    @ApiOperation("获取题目详情")
    public Object getProblem(@PathVariable Long id){
        return null;
    }

    @PostMapping
    @ApiOperation("创建或修改题目")
    public Object createOrModifyProblem(){
        return null;
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除题目")
    public Object deleteProblem(@PathVariable Long id){
        return null;
    }

    @GetMapping
    @ApiOperation("获取全部题目")
    public Object getProblem(){
        return null;
    }

    @GetMapping("/lists")
    @ApiOperation("获取全部题单")
    public Object getLists(){
        return null;
    }

    @GetMapping("/list/{id}")
    @ApiOperation("获取题单基础信息")
    public Object getListInfo(@PathVariable Long id){
        return null;
    }

    @PostMapping("/list")
    @ApiOperation("创建或修改一个题单")
    public Object createOrModifyProblemList(){
        return null;
    }

    @DeleteMapping("/list/{id}")
    @ApiOperation("删除某个题单")
    public Object deleteProblemList(@PathVariable Long id){
        return null;
    }

    @GetMapping("/list/{id}/problems")
    @ApiOperation("获取题单中的所有题目")
    public Object getProblemsByList(@PathVariable Long id){
        return null;
    }

    @PostMapping("/list/{id}")
    @ApiOperation("为题单新增题目")
    public Object addProblemToList(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/{problemId}/list/{listId}/")
    @ApiOperation("删除题单下的某个题目")
    public Object deleteProblemFromList(@PathVariable Long problemId,@PathVariable Long listId){
        return null;
    }

    @GetMapping("/tags")
    @ApiOperation("获取所有标签")
    public Object getTags(){
        return null;
    }

    @PostMapping("/tag")
    @ApiOperation("创建或修改某个标签")
    public Object createOrModifyTag(){
        return null;
    }

    @DeleteMapping("/tag/{id}")
    @ApiOperation("删除某个标签")
    public Object deleteTag(@PathVariable Long id){
        return null;
    }

    @GetMapping("/tag/{id}/problems")
    @ApiOperation("获取标签下(题目类型)的所有题目")
    public Object getProblemsByTags(@PathVariable Long id){
        return null;
    }

    @PostMapping("/tag/{id}")
    @ApiOperation("为标签(题目类型)新增题目")
    public Object addProblemToTag(@PathVariable Long id){
        return null;
    }

    @DeleteMapping("/{problemId}/tag/{tagId}/")
    @ApiOperation("删除标签下的某个题目")
    public Object deleteProblemFromTag(@PathVariable Long problemId,@PathVariable Long tagId){
        return null;
    }

    @GetMapping("/contest")
    @ApiOperation("获取全部竞赛")
    public Object getContests(){
        return null;
    }

    @GetMapping("/contest/{id}")
    @ApiOperation("获取竞赛基础信息")
    public Object getContestInfo(@PathVariable Long id){
        return null;
    }

    @PostMapping("/contest")
    @ApiOperation("创建或修改一个竞赛")
    public Object createOrModifyProblemToList(){
        return null;
    }

    @DeleteMapping("/contest/{id}")
    @ApiOperation("删除某个竞赛")
    public Object deleteContest(@PathVariable Long id){
        return null;
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

    @PostMapping("{id}/judge")
    @ApiOperation("判题")
    public Object judgeProblem(@PathVariable Long id){
        return null;
    }
}