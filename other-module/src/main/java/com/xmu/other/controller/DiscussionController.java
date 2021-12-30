package com.xmu.other.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.server.HttpServerRequest;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.IpUtil;
import com.xmu.common.utils.Jwt;
import com.xmu.common.utils.Response;
import com.xmu.other.service.ReplyService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import request.ReplyDTO;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/discussion")
public class DiscussionController {

    @Autowired
    ReplyService replyService;

    @GetMapping("/{problemId}")
    @ApiOperation("获取某个问题下的评论")
    public Object getCommentsByProblemId(@PathVariable Long problemId){
        return replyService.getCommentsByProblemId(problemId);
    }
    @GetMapping("/detail/{problemId}")
    @ApiOperation("获取某个问题下的详细评论(仅管理员)")
    public Object getDetailCommentsByProblemId(@PathVariable Long problemId,HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        if(token == null){
            Response.of(ResponseCode.AUTHORIZATION_TOKEN_NOT_EXIST);
        }
        Long userId = Jwt.getUserIdFromToken(token);
        return replyService.getDetailCommentsByProblemId(problemId,userId);
    }

    @PostMapping("/{problemId}")
    @ApiOperation("用户就某个问题发表评论")
    public Object createOrModifyComment(@PathVariable Long problemId, @RequestBody ReplyDTO replyDTO, HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        if(token == null){
            Response.of(ResponseCode.AUTHORIZATION_TOKEN_NOT_EXIST);
        }
        Long userId = Jwt.getUserIdFromToken(token);
        replyDTO.setUserId(userId);
        replyDTO.setIp(IpUtil.getIpAddress(httpRequest));
        replyDTO.setTime(LocalDateTime.now());
        replyDTO.setProblemId(problemId);
        return replyService.createOrModifyComment(replyDTO);
    }

    @DeleteMapping("/comment/{commentId}")
    @ApiOperation("用户删除某个评论")
    public Object deleteComment(@PathVariable Long commentId, HttpServletRequest httpRequest){
        String token = httpRequest.getHeader("Authorization");
        Long userId = Jwt.getUserIdFromToken(token);
        return replyService.deleteComment(commentId,userId);
    }
}
