package com.xmu.problem.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemBasicInfo {
    private Long id;
    private String title;
    private String description;
    private String sampleInput;
    private String sampleOutput;
    private Integer timeLimit;
    private Integer memoryLimit;
    private List<String> categories;
    private List<String> lists;
    private String difficulty;
}
