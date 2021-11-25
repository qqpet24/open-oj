package com.xmu.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
    private Integer defunct;
    private String ip;
    private Date registerTime;
    private Date accessTime;
    private Long roleId;
}



