package com.xmu.auth.domain;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class UserProfile {
    @TableId(type= IdType.AUTO)
    private Long id;
    private Long userId;
    private String email;
    private Integer submit;
    private Integer solved;
    private String school;
    private String studentNo;
    private Integer score;
}
