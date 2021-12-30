package com.xmu.problem.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xmu.problem.reponse.ContestBriefInfoDTO;
import com.xmu.problem.reponse.ContestDetailInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
@TableName(autoResultMap = true)
public class Contest {
    private Long id;
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime endTime;
    private String description;
    @TableField("private")
    private Integer isPrivate;
    private Integer langMask;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> problems;
    private Long userId;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> participants;

    public ContestBriefInfoDTO brief() {
        ContestBriefInfoDTO contestBriefInfoDTO = new ContestBriefInfoDTO();
        BeanUtils.copyProperties(this, contestBriefInfoDTO);
        return contestBriefInfoDTO;
    }

    public ContestDetailInfoDTO detail(String creator){
        ContestDetailInfoDTO contestDetailInfoDTO=new ContestDetailInfoDTO();
        BeanUtils.copyProperties(this,contestDetailInfoDTO);
        contestDetailInfoDTO.setCreator(creator);
        return contestDetailInfoDTO;
    }
}
