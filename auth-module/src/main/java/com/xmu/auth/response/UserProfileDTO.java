package com.xmu.auth.response;

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
public class UserProfileDTO {
    private Long id;
    private String username;
    private String email;
    private Integer submit;
    private Integer solved;
    private String school;
    private Integer score;
    private String avatar;
}
