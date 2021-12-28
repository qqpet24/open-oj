package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.ProblemList;
import com.xmu.problem.request.ProblemListDTO;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface ProblemListService extends IService<ProblemList> {
    Object getProblemList();

    Object getListInfo(Long id);

    Object createOrModifyProblemList(ProblemListDTO problemListDTO);

    Object deleteProblemList(Long id);
}
