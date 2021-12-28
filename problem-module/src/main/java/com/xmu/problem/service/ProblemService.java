package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.request.ProblemDTO;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface ProblemService extends IService<Problem> {
    Object getProblems();

    Object getProblem(Long id);

    Object deleteProblem(Long id);

    Object createOrModifyProblem(ProblemDTO problem);
}
