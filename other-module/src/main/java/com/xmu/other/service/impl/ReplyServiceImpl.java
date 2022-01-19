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
import java.util.stream.Collectors;

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
            Long originalDetail = reply.getDetail();
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

                Long originalProblemId = reply.getProblemId();
                BeanUtils.copyProperties(replyDTO,reply);
                reply.setDetail(originalDetail);
                reply.setProblemId(originalProblemId);
            }else{//如果是管理员，管理员修改评论不应该改变原来发表评论的用户ID、时间、IP
                Long originalUserId = reply.getUserId();
                LocalDateTime originalTime = reply.getTime();
                String originalIp = reply.getIp();
                Long originalProblemId = reply.getProblemId();

                BeanUtils.copyProperties(replyDTO,reply);

                reply.setUserId(originalUserId);
                reply.setIp(originalIp);
                reply.setTime(originalTime);
                reply.setDetail(originalDetail);
                reply.setProblemId(originalProblemId);
            }
        }else{
            reply = new Reply();
            if(replyDTO.getDetail()!=null){//如果是对一个问题评论的评论
                Reply topicReply = this.getById(replyDTO.getDetail());
                if(topicReply==null){
                    return Response.of(ResponseCode.COMMENT_NOT_EXIST);
                }
                if(topicReply.getProblemId().compareTo(replyDTO.getProblemId())!=0){
                    return Response.of(ResponseCode.PROBLEM_NOT_MATCH);
                }
            }
            BeanUtils.copyProperties(replyDTO,reply);
            reply.setId(null);
            reply.setStatus(0);
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

        if(reply==null){
            return Response.of(ResponseCode.COMMENT_NOT_EXIST);
        }

        if(roleId!=1){//如果不是管理员且删除的不是自己的评论
            if(!Objects.equals(reply.getUserId(), userId)){
                return Response.of(ResponseCode.NOT_INITIAL_COMMENT_USER);
            }
            if(reply.getStatus().compareTo(1)==0){//已经被冻结评论不能删除
                return Response.of(ResponseCode.COMMENT_FROZEN);
            }
        }

        boolean result = false;

        if(reply.getDetail()==null){//删除一条评论后，评论的评论都要被删除
            QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("detail",reply.getId());
            List<Reply> replyList = this.list(queryWrapper);
            replyList.add(reply);
            List<Long> replyIdList = replyList.stream().map(Reply::getId).collect(Collectors.toList());
            result = this.removeByIds(replyIdList);
        }else{
            result = this.removeById(commentId);
        }

        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.COMMENT_NOT_EXIST);
        }
    }

    @Override
    public Object getCommentsByProblemId(Long problemId, Long detail) {//已经冻结的评论普通用户不能获取
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id",problemId);
        queryWrapper.eq("status",0);
        if(detail==null){
            queryWrapper.isNull("detail");
        }else{
            queryWrapper.eq("detail",detail);
        }

        List<Reply> replyList = this.list(queryWrapper);
        List<ReplyBriefDTO> replyDTOList = replyList.stream().map(Reply::toReplyBriefDTO).collect(Collectors.toList());
        return Response.of(ResponseCode.OK,replyDTOList);
    }

    @Override
    public Object getDetailCommentsByProblemId(Long problemId, Long userId, Long detail) {
        User user = userService.getById(userId);
        if(user == null){//校验用户是否存在
            return Response.of(ResponseCode.USER_NOT_EXIST);
        }
        if(user.getRoleId().compareTo(1L)!=0){//如果不是管理员
            return Response.of(ResponseCode.NOT_ADMIN);
        }
        QueryWrapper<Reply> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("problem_id",problemId);
        if(detail==null){
            queryWrapper.isNull("detail");
        }else{
            queryWrapper.eq("detail",detail);
        }
        List<Reply> replyList = this.list(queryWrapper);
        List<ReplyDetailDTO> replyDTOList = replyList.stream().map(Reply::toReplyDetailDTO).collect(Collectors.toList());
        return Response.of(ResponseCode.OK,replyDTOList);
    }
}
