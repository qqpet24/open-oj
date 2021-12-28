package com.xmu.problem.reponse;

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
public class ContestBriefInfoDTO {
    private Long id;
    private String title;
    private String description;
    private String startTime;
    private String endTime;
    private String continuousTime;
    private Long userId;
    private String creator;
    private String avatar;
}
