package com.xmu.problem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class FixTimeSolution {//返回值中修复时间返回的solution
    private Long id;
    private Long problemId;
    private Long userId;
    private Integer time;
    private Integer memory;
    private String inDate;
    private String result;
    private String language;
    private String ip;
    private Integer valid;
    private Integer codeLength;
    private String judgetime;
    private Double pass_rate;
    public FixTimeSolution(Solution solution){
        this.id = solution.getId();
        this.problemId = solution.getProblemId();
        this.userId = solution.getUserId();
        this.time = solution.getTime();
        this.memory = solution.getMemory();
        this.inDate = solution.getInDate().toString();
        this.result = solution.getResult();
        this.language = solution.getLanguage();
        this.ip = solution.getIp();
        this.valid = solution.getValid();
        this.codeLength = solution.getCodeLength();
        this.judgetime = solution.getJudgetime().toString();
        this.pass_rate = solution.getPass_rate();
    }
}
