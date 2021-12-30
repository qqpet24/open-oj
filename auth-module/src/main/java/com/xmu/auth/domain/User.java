package com.xmu.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xmu.auth.response.UserProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class User {
    @TableId(type= IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String nickname;
    //private Integer defunct;
    private String ip;
    private LocalDateTime registerTime;
    private LocalDateTime accessTime;
    private Long roleId;
    private String email;
    private Integer submit;
    private Integer solved;
    private String school;
    private Integer score;
    private String avatar;

    public UserProfileDTO profile(){
        UserProfileDTO userProfileDTO = new UserProfileDTO();
        BeanUtils.copyProperties(this,userProfileDTO);
        return userProfileDTO;
    }
}



