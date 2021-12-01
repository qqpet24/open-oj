package com.xmu.other.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
    private Long id;
    private Long userId;
    private LocalDateTime time;
    private String text;
    private Integer topicId;
    private Integer status;
    private String ip;
}
