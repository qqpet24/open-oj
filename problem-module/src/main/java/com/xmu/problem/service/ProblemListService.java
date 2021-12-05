package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.ProblemList;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface ProblemListService extends IService<ProblemList> {
    Object getProblemList();
}
