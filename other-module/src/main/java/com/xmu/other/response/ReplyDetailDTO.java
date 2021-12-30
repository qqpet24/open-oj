package com.xmu.other.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class ReplyDetailDTO {
    private Long id;
    private Long userId;
    private LocalDateTime time;
    private String text;
    private Integer status;
    private String ip;
    private Long problemId;
}
