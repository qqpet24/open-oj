package com.xmu.problem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.xmu.problem.reponse.ProblemBriefDTO;
import com.xmu.problem.request.ProblemDTO;
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
public class Problem {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String inputDescription;
    private String outputDescription;
    private String sampleInput;
    private String sampleOutput;
    private String source;
    private LocalDateTime inDate;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer accepted;
    private Integer submit;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> categories;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> lists;
    private Integer difficulty;
    private Integer star;

    public ProblemBriefDTO brief() {
        ProblemBriefDTO problemBrief = new ProblemBriefDTO();
        BeanUtils.copyProperties(this, problemBrief);
        switch (difficulty) {
            case 0 -> problemBrief.setDifficulty("simple");
            case 1 -> problemBrief.setDifficulty("medium");
            case 2 -> problemBrief.setDifficulty("hard");
        }
        return problemBrief;
    }

    public Problem(ProblemDTO problemDTO) {
        BeanUtils.copyProperties(problemDTO, this);
    }
}
