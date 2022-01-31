package com.xmu.problem.request.util;

import jnr.ffi.annotations.In;
import lombok.Getter;

@Getter
public enum JudgeStatus {
    ACCEPTED(0,"AC"),
    COMPILE_ERROR(1,"CE"),
    COMPILE_TIME_LIMIT_EXCEED(2,"CTLE"),
    EXECUTION_TIME_LIMIT_EXCEED(3,"ETLE"),
    MEMORY_LIMIT_EXCEED(4,"MLE"),
    RUNTIME_ERROR(5,"RE"),
    WRONG_ANSWER(6,"WA"),
    UNKNOWN_ERROR(7,"UE"),
    COMPILE_SUCCESS(8,"CS");
    private final Integer code;
    private final String type;

    JudgeStatus(Integer c,String e){
        this.type=e;
        this.code=c;
    }
}
