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
public class Message {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String title;
    private String content;
    private Integer reply;
    private LocalDateTime inDate;
}
