package com.xmu.problem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.problem.domain.Contest;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface ContestService extends IService<Contest> {
    Object getContests();

    Object getContestInfo(Long id);

    Object deleteContest(Long id);
}
