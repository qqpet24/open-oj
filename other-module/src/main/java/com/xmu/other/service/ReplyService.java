package com.xmu.other.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.other.domain.Reply;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import request.ReplyDTO;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Mapper
public interface ReplyService extends IService<Reply> {
    Object createOrModifyComment(ReplyDTO replyDTO);

    Object deleteComment(Long commentId, Long userId);

    Object getCommentsByProblemId(Long problemId, Long detail);

    Object getDetailCommentsByProblemId(Long problemId, Long userId, Long detail);
}
