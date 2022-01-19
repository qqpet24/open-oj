package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.auth.domain.User;
import com.xmu.auth.service.UserService;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.DateTimeUtil;
import com.xmu.common.utils.Response;
import com.xmu.problem.domain.Contest;
import com.xmu.problem.mapper.ContestMapper;
import com.xmu.problem.reponse.ContestBriefInfoDTO;
import com.xmu.problem.service.ContestService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ContestServiceImpl extends ServiceImpl<ContestMapper, Contest> implements ContestService {

    @Autowired
    private UserService userService;

    @Override
    public Object getContests() {
        List<Contest> contests = this.list(Wrappers.lambdaQuery());
        List<ContestBriefInfoDTO> contestBriefInfoDTOS = contests.stream().map(contest -> {
            ContestBriefInfoDTO contestBriefInfoDTO = new ContestBriefInfoDTO();
            BeanUtils.copyProperties(contest, contestBriefInfoDTO);
            return brief(contest, contestBriefInfoDTO);
        }).collect(Collectors.toList());

        return Response.of(ResponseCode.OK, contestBriefInfoDTOS);
    }

    private ContestBriefInfoDTO brief(Contest contest, ContestBriefInfoDTO contestBriefInfoDTO) {
        Long userId = contest.getUserId();
        User user = userService.getById(userId);
        contestBriefInfoDTO.setAvatar(user.getAvatar());
        contestBriefInfoDTO.setCreator(user.getUsername());
        contestBriefInfoDTO.setUserId(userId);
        contestBriefInfoDTO.setContinuousTime(DateTimeUtil.between(contest.getEndTime(), contest.getStartTime()));
        contestBriefInfoDTO.setStartTime(contest.getStartTime().toString());
        contestBriefInfoDTO.setEndTime(contest.getEndTime().toString());
        return contestBriefInfoDTO;
    }

    @Override
    public Object getContestInfo(Long id) {
        Contest contest = this.getById(id);
        User creator = userService.getById(contest.getUserId());
        return Response.of(ResponseCode.OK, contest.detail(creator.getUsername()));
    }

    @Override
    public Object deleteContest(Long id) {
        Contest contest;
        if ((contest = this.getById(id)) == null) {
            return Response.of(ResponseCode.NOT_FOUND);
        }
        if (this.removeById(contest)) {
            return Response.of(ResponseCode.OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Object createOrModifyContest(Contest contest) {
        if (contest == null) {
            return Response.of(ResponseCode.BAD_REQUEST);
        }
        String title = contest.getTitle();
        Contest contestTemp = this.getOne(Wrappers.<Contest>lambdaQuery().eq(Contest::getTitle, title));
        if (contestTemp == null) {
            if (this.save(contest)) {
                return Response.of(ResponseCode.OK);
            } else {
                return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
            }
        }
        BeanUtils.copyProperties(contest, contestTemp);
        if (this.updateById(contestTemp)) {
            return Response.of(ResponseCode.OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Object getProblemsFromContest(Long id) {
        Contest contest;
        if ((contest = this.getById(id)) == null) {
            return Response.of(ResponseCode.NOT_FOUND).entity(HttpStatus.NOT_FOUND);
        }
        List<?> problems;
        if ((problems = contest.getProblems()) == null) {
            return Response.of(ResponseCode.NOT_FOUND).entity(HttpStatus.NOT_FOUND);
        }
        return Response.of(ResponseCode.OK, problems).entity(HttpStatus.OK);
    }


}
