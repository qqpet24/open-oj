package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.domain.ProblemList;
import com.xmu.problem.mapper.ProblemListMapper;
import com.xmu.problem.reponse.ProblemListDTO;
import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.ProblemListService;
import org.springframework.beans.BeanUtils;
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

    @Override
    public Object getListInfo(Long id) {
        ProblemList problemList = this.getById(id);
        if(problemList!=null){
            ProblemListDTO problemListDTO = new ProblemListDTO();
            BeanUtils.copyProperties(problemList,problemListDTO);
            return Response.of(ResponseCode.OK, problemListDTO);
        }else{
            return Response.of(ResponseCode.PROBLEM_LIST_NOT_EXIST);
        }
    }

    @Override
    public Object createOrModifyProblemList(com.xmu.problem.request.ProblemListDTO problemListDTO) {
        ProblemList problemList = new ProblemList();
        BeanUtils.copyProperties(problemListDTO,problemList);

        boolean result = this.saveOrUpdate(problemList);

        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Object deleteProblemList(Long id) {
        boolean result = this.removeById(id);
        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.PROBLEM_LIST_NOT_EXIST);
        }
    }
}
