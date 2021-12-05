package com.xmu.problem.controller;

import com.xmu.problem.service.ProblemListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/problem")
public class ProblemListController {

    @Autowired
    private ProblemListService problemListService;

    @GetMapping("/all")
    public Object getProblemList(){
        return problemListService.getProblemList();
    }
}
