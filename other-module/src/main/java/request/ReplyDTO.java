package request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class ReplyDTO {
    //下面只能增加不能修改，有函数中用到
    private Long id;//如果新增，前端无需传入，如果修改则需要
    private Long userId;//前端无需传入
    private LocalDateTime time;//前端无需传入
    private String text;
    private Integer status;//前端非管理员无需传入
    private String ip;//前端无需传入
    private Long problemId;//前端在path中传入，此处无需传入
}
