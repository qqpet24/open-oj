package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.Category;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface CategoryService extends IService<Category> {
    Object getCategories();
}
