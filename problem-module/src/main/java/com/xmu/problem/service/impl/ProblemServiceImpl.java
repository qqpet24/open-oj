package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.mapper.ProblemMapper;
import com.xmu.problem.reponse.ProblemBriefDTO;
import com.xmu.problem.request.ProblemDTO;
import com.xmu.problem.service.ProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {

    @Override
    public Object getProblems() {
        List<ProblemBriefDTO> problemBriefs
                = this.list(Wrappers.lambdaQuery()).stream().map(Problem::brief).toList();
        return Response.of(ResponseCode.OK,problemBriefs);
    }

    @Override
    public Object getProblem(Long id) {
        Problem problem = this.getById(id);
        if(problem!=null){
            com.xmu.problem.reponse.ProblemDTO problemDTO = new com.xmu.problem.reponse.ProblemDTO();
            BeanUtils.copyProperties(problem,problemDTO);
            problemDTO.setInDate(problem.getInDate().toString());
            return Response.of(ResponseCode.OK,problemDTO);
        }else{
            return Response.of(ResponseCode.PROBLEM_NOT_EXIST);
        }
    }

    @Override
    public Object deleteProblem(Long id) {
        boolean result = this.removeById(id);
        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.PROBLEM_NOT_EXIST);
        }
    }

    @Override
    public Object createOrModifyProblem(ProblemDTO problem) {
        Problem problem1 = new Problem(problem);
        boolean result = this.saveOrUpdate(problem1);

        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
