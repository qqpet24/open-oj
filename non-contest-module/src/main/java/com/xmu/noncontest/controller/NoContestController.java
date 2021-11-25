package com.xmu.noncontest.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/problem")
public class NoContestController {

    @GetMapping
    public Object getProblems(){
        return null;
    }

    @GetMapping("{problemId}")
    public Object getProblemById(@PathVariable Long problemId){
        return null;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{problemId}")
    public Object deleteProblem(@PathVariable Long problemId){
        return null;
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public Object createOrModifyProblem(){
        return null;
    }
}
