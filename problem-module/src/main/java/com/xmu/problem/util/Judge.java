package com.xmu.problem.util;

import java.io.IOException;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface Judge {

    void compile() throws IOException, InterruptedException;

    void execute() throws IOException, InterruptedException;

    JudgeResult check();
}
