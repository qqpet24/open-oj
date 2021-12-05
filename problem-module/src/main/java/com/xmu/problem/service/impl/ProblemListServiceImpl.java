package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.ProblemList;
import com.xmu.problem.mapper.ProblemListMapper;
import com.xmu.problem.service.ProblemListService;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ProblemListServiceImpl extends ServiceImpl<ProblemListMapper, ProblemList> implements ProblemListService {

    @Override
    public Object getProblemList() {
        List<ProblemList> list = this.list(Wrappers.lambdaQuery());
        return Response.of(ResponseCode.OK, list).entity(OK);
    }
}
