package com.xmu.other.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class MailRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("`time`")
    private LocalDateTime timeValue;
    @TableField("`from`")
    private String fromValue;
    @TableField("`to`")
    private String toValue;
    @TableField("`status`")
    private Integer statusValue;
    @TableField("`subject`")
    private String subjectValue;
    @TableField("`text`")
    private String textValue;
}
