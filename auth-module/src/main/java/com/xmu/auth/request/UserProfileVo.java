package com.xmu.auth.request;
import com.xmu.auth.domain.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class UserProfileVo {
    private Long userId;
    private String email;
    private Integer submit;
    private Integer solved;
    private String school;
    private String studentNo;
    private Integer score;

    public UserProfile userProfile(){
        UserProfile userProfile = new UserProfile();
        BeanUtils.copyProperties(this,userProfile);
        return userProfile;
    }

    public void userProfile(UserProfile userProfile){
        BeanUtils.copyProperties(this,userProfile);
    }
}
