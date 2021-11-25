package com.xmu.auth.request;

import com.xmu.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private String username;
    private String password;

    public User User(){
        User user = new User();
        BeanUtils.copyProperties(this,user);
        return user;
    }
}
