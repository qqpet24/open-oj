package com.xmu.other.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.xmu.other.response.BulletinDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class Bulletin {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime time;
    private Integer importance;
    public BulletinDTO toDTO(){
        BulletinDTO bulletinDTO = new BulletinDTO();
        BeanUtils.copyProperties(this,bulletinDTO);
        bulletinDTO.setTime(this.time.toString());
        return bulletinDTO;
    }
}
