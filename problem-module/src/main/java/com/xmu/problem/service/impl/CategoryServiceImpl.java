package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.Category;
import com.xmu.problem.mapper.CategoryMapper;
import com.xmu.problem.service.CategoryService;
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
}
