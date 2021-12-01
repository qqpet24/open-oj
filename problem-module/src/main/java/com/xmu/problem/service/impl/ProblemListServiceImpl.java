package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.problem.domain.ProblemList;
import com.xmu.problem.mapper.ProblemListMapper;
import com.xmu.problem.service.ProblemListService;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ProblemListServiceImpl extends ServiceImpl<ProblemListMapper, ProblemList> implements ProblemListService {
}
