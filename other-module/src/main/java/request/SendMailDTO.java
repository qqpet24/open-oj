package request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class SendMailDTO {
    private List<String> toList;
    private List<String> subjectList;
    private List<String> textList;
}
