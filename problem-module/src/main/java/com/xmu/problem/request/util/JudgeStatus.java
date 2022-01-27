package com.xmu.problem.request.util;

import jnr.ffi.annotations.In;
import lombok.Getter;

@Getter
public enum JudgeStatus {
    ACCEPTED(0,"accepted");
    private final Integer code;
    private final String type;

    JudgeStatus(Integer c,String e){
        this.type=e;
        this.code=c;
    }
}
