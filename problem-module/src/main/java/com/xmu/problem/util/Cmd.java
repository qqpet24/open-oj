package com.xmu.problem.util;

import lombok.Getter;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Getter
public enum Cmd {
    JAVA("java", "javac ", "java "),
    C("c","gcc","");
    private final String language;
    private final String cmdCompile;
    private final String cmdExecute;

    Cmd(String language, String cmdCompile, String cmdExecute) {
        this.language = language;
        this.cmdCompile = cmdCompile;
        this.cmdExecute = cmdExecute;
    }
}
