package com.xmu.other.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.auth.domain.User;
import com.xmu.auth.service.UserService;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.other.domain.Reply;
import com.xmu.other.mapper.ReplyMapper;
import com.xmu.other.response.ReplyBriefDTO;
import com.xmu.other.response.ReplyDetailDTO;
import com.xmu.other.service.ReplyService;
import com.xmu.problem.domain.Problem;
import com.xmu.problem.service.ProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import request.ReplyDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, Reply> implements ReplyService {
    @Autowired
    private UserService userService;
    @Autowired
    private ProblemService problemService;

    @Override
    public Object createOrModifyComment(ReplyDTO replyDTO) {
        User user = userService.getById(replyDTO.getUserId());
        if(user == null){//校验用户是否存在
            return Response.of(ResponseCode.USER_NOT_EXIST);
        }
        Problem problem = problemService.getById(replyDTO.getProblemId());
        if(problem==null || problem.getId()==null){//校验题目是否存在
            return Response.of(ResponseCode.PROBLEM_NOT_EXIST);
        }

        Reply reply = this.getById(replyDTO.getId());
        if(reply!=null){
            Long roleId = user.getRoleId();
            if(roleId!=1){//如果不是管理员
                replyDTO.setStatus(null);
                if(reply.getStatus().compareTo(0)!=0){//评论冻结
                    return Response.of(ResponseCode.COMMENT_FROZEN);
                }else{
                    reply.setStatus(0);
                }
                if(!Objects.equals(replyDTO.getUserId(), reply.getUserId())){//如果修改的不是自己的评论
                    return Response.of(ResponseCode.NOT_INITIAL_COMMENT_USER);
                }
                BeanUtils.copyProperties(replyDTO,reply);
            }else{//如果是管理员，管理员修改评论不应该改变原来发表评论的用户ID、时间、IP
                Long originalUserId = reply.getUserId();
                LocalDateTime originalTime = reply.getTime();
                String originalIp = reply.getIp();

                BeanUtils.copyProperties(replyDTO,reply);

                reply.setUserId(originalUserId);
                reply.setIp(originalIp);
                reply.setTime(originalTime);
            }
        }else{
            reply = new Reply();
            BeanUtils.copyProperties(replyDTO,reply);
            reply.setId(null);
        }

        boolean result = this.saveOrUpdate(reply);

        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Object deleteComment(Long commentId, Long userId) {
        Reply reply = this.getById(commentId);
        Long roleId = userService.getById(userId).getRoleId();

        if(roleId!=1 && reply != null){//如果不是管理员且删除的不是自己的评论
            if(!Objects.equals(reply.getUserId(), userId)){
                return Response.of(ResponseCode.NOT_INITIAL_COMMENT_USER);
            }
            if(reply.getStatus().compareTo(1)==0){//已经被冻结评论不能修改
                return Response.of(ResponseCode.COMMENT_FROZEN);
            }
        }

        boolean result = this.removeById(commentId);
        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.COMMON_NOT_EXIST);
        }
    }

    @Override
    public Object getCommentsByProblemId(Long problemId) {
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id",problemId);
        queryWrapper.eq("status",0);
        List<Reply> replyList = this.list(queryWrapper);
        List<ReplyBriefDTO> replyDTOList = replyList.stream().map(Reply::toReplyBriefDTO).toList();
        return Response.of(ResponseCode.OK,replyDTOList);
    }

    @Override
    public Object getDetailCommentsByProblemId(Long problemId, Long userId) {
        User user = userService.getById(userId);
        if(user == null){//校验用户是否存在
            return Response.of(ResponseCode.USER_NOT_EXIST);
        }
        if(user.getRoleId().compareTo(1L)!=0){//如果不是管理员
            return Response.of(ResponseCode.NOT_ADMIN);
        }
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id",problemId);
        queryWrapper.eq("status",0);
        List<Reply> replyList = this.list(queryWrapper);
        List<ReplyDetailDTO> replyDTOList = replyList.stream().map(Reply::toReplyDetailDTO).toList();
        return Response.of(ResponseCode.OK,replyDTOList);
    }
}
