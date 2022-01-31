package com.xmu.problem.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class JudgeResultDTO {
    private Long solutionId;
    private String compileStatus;// null if compile successful
    private List<JudgeReturnInfo> executeInfo;
    private String compileError;// null if compile successful
}
