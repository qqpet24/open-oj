package com.xmu.problem.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@Accessors(chain = true)
public class JudgeReturnInfo {
    private String status;
    //测试用例的文件名
    private String testCase;
    //unit ms
    private Long executeTime;
    //unit kb
    private Long executeMem;
    //错误
    private String errorInfo;
}
