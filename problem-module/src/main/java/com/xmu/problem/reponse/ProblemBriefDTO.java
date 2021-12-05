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
public class ProblemBriefDTO {

    private Long id;
    private String title;
    private String description;
    private Integer difficulty;
    private List<String> categories;
    private Integer star;
}
