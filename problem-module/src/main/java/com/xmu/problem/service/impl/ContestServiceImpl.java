package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.problem.domain.Contest;
import com.xmu.problem.mapper.ContestMapper;
import com.xmu.problem.service.ContestService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest> implements ContestService {
    @Override
    public Object getContests() {
        List<Contest> contests = this.list();
        return contests;
    }

    @Override
    public Object getContestInfo(Long id) {
        return null;
    }

    @Override
    public Object deleteContest(Long id) {
        return null;
    }


}
