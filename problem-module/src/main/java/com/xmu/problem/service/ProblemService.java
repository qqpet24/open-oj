package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.request.JudgeDTO;
import com.xmu.problem.request.ProblemDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface ProblemService extends IService<Problem> {
    Object getProblems();

    Object getProblem(Long id);

    Object deleteProblem(Long id);

    Object createOrModifyProblem(ProblemDTO problem);

    Object judge(Long id, JudgeDTO judgeDTO, HttpServletRequest request) throws Exception;

    Object judgev2(Long id, JudgeDTO judgeDTO, HttpServletRequest request) throws Exception;

    Object getBasicProblemInfo(Long id);

    Object getTestCaseNameById(Long id);
}
