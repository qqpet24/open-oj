package com.xmu.problem.controller;

import com.xmu.problem.domain.Category;
import com.xmu.problem.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @ApiOperation("获取全部题目标签")
    public Object getCategories(){
        return categoryService.getCategories();
    }

    @PostMapping
    @ApiOperation("创建或修改某个标签")
    public Object createOrModifyTag(@RequestBody Category category){
        return categoryService.createOrModifyTag(category);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除某个标签")
    public Object deleteTag(@PathVariable Long id){
        return categoryService.deleteTag(id);
    }

    @GetMapping("/{id}/problems")
    @ApiOperation("获取标签下(题目类型)的所有题目")
    public Object getProblemsByTags(@PathVariable Long id){
        return categoryService.getProblemsByTags(id);
    }

}
