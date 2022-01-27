package com.xmu.problem.request.util;

import com.xmu.problem.reponse.JudgeResultDTO;

import java.io.IOException;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface Judge {

    void compile() throws IOException, InterruptedException;

    void execute() throws IOException, InterruptedException;

    JudgeResultDTO check();
}
