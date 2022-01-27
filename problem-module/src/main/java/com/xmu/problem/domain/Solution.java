package com.xmu.problem.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class Solution {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long problemId;
    private Long userId;
    private Integer time;
    private Integer memory;
    private LocalDateTime inDate;
    private Short result;
    private Integer language;
    private String ip;
    private Integer valid;
    private Integer codeLength;
    private LocalDateTime judgeTime;
    private Double pass_rate;
}
