package com.xmu.problem.reponse;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class ProblemDTO {
    private Long id;
    private String title;
    private String description;
    private String inputDescription;
    private String outputDescription;
    private String sampleInput;
    private String sampleOutput;
    private String source;
    private String inDate;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer accepted;
    private Integer submit;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> categories;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> lists;
    private Integer difficulty;
    private Integer star;
}
