package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.Category;
import com.xmu.problem.mapper.CategoryMapper;
import com.xmu.problem.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Override
    public Object getCategories() {
        List<Category> list = this.list(Wrappers.lambdaQuery());
        return Response.of(ResponseCode.OK, list);
    }

    @Override
    public Object createOrModifyTag(Category category) {
        if (category == null) {
            return Response.of(ResponseCode.BAD_REQUEST);
        }
        Category categoryTemp;
        if ((categoryTemp
                = this.getOne(Wrappers.<Category>lambdaQuery().eq(Category::getName, category.getName()))) == null) {
            if (this.save(category)) {
                return Response.of(ResponseCode.OK);
            } else {
                return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
            }
        }
        BeanUtils.copyProperties(category, categoryTemp);
        if (this.updateById(categoryTemp)) {
            return Response.of(ResponseCode.OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Object deleteTag(Long id) {
        if (this.removeById(id)) {
            return Response.of(ResponseCode.OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Object getProblemsByTags(Long id) {
        Category category;
        if((category=this.getById(id))==null){
            return Response.of(ResponseCode.NOT_FOUND);
        }
        return Response.of(ResponseCode.OK,category.getProblems());
    }
}
