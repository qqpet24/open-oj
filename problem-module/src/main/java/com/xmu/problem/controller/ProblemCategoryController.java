package com.xmu.problem.controller;

import com.xmu.problem.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/category")
public class ProblemCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    @ApiOperation("获取全部题目种类")
    public Object getCategories(){
        return categoryService.getCategories();
    }
}
