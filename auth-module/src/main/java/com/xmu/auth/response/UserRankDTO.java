package com.xmu.auth.response;

import com.xmu.auth.domain.User;
import jnr.ffi.annotations.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class UserRankDTO {
    private Long id;
    private String userName;
    private String avatar;
    private Integer score;
    public UserRankDTO(User user){
        this.id = user.getId();
        this.userName = user.getUsername();
        this.avatar = user.getAvatar();
        this.score = user.getScore();
    }
}
