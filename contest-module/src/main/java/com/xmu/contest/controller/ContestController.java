package com.xmu.contest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contest")
//未经测试的注解
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class ContestController {

    @GetMapping
    public Object  getContests(){
        return null;
    }

    @GetMapping("/{contestId}")
    public Object getContest(@PathVariable Long contestId){
        return null;
    }

    @DeleteMapping("/{contestId}")
    public Object deleteContest(@PathVariable Long contestId){
        return null;
    }

    @PostMapping
    public Object createOrModifyContest(){
        return null;
    }

    @GetMapping("/{contestId}/problems")
    public Object getProblemsByContestId(@PathVariable Long contestId){
        return null;
    }

    @PostMapping("{contestId}/problem")
    public Object addProblem(@PathVariable Long contestId){
        return null;
    }

    //竞赛中的题目和题单中的题目应该是同级别的（修改和新增都不需要）
    @PutMapping("{contestId}/problem/{problemId}")
    public Object modifyProblemInContest(@PathVariable Long contestId,@PathVariable Long problemId){
        return null;
    }

    @DeleteMapping("{contestId}/problem/{problemId}")
    public Object deleteProblemInContest(@PathVariable Long contestId,@PathVariable Long problemId){
        return null;
    }

    @PutMapping("{contestId}/visible")
    public Object changeContestVisible(@PathVariable Long contestId){
        return null;
    }
}

