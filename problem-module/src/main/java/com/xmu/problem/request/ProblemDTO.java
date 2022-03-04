package com.xmu.problem.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class ProblemDTO {

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> categories;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<?> lists;
    private String difficulty;
}
