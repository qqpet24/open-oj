package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.Problem;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface ProblemService extends IService<Problem> {
    Object getProblems();

    Object getProblem(Long id);

}
