package com.xmu.common.enums;

import lombok.Getter;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Getter
public enum Defunct {
    DEFUNCT(1, "defunct"),
    NOT_DEFUNCT(0, "not defunct");

    private final int code;
    private final String msg;


    Defunct(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
