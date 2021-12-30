package com.xmu.other.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xmu.other.response.ReplyBriefDTO;
import com.xmu.other.response.ReplyDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class Reply {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private LocalDateTime time;
    private String text;
    private Integer status;
    private String ip;
    private Long problemId;
    private Long detail;
    public ReplyBriefDTO toReplyBriefDTO(){
        ReplyBriefDTO replyDTO = new ReplyBriefDTO();
        BeanUtils.copyProperties(this,replyDTO);
        return replyDTO;
    }
    public ReplyDetailDTO toReplyDetailDTO(){
        ReplyDetailDTO replyDTO = new ReplyDetailDTO();
        BeanUtils.copyProperties(this,replyDTO);
        return replyDTO;
    }
}
