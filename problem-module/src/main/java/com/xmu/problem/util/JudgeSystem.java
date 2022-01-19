package com.xmu.problem.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public class JudgeSystem implements Judge {

    private final String workDir;
    private final String compileCmd;
    private final String executeCmd;
    private final String standardInputPath;
    private final String standardOutputPath;
    private byte[] userOutput;

    public JudgeSystem(String workDir,String compileCmd, String executeCmd, String standardInputPath, String standardOutputPath) {
        this.workDir=workDir;
        this.compileCmd = compileCmd;
        this.executeCmd = executeCmd;
        this.standardInputPath = standardInputPath;
        this.standardOutputPath = standardOutputPath;
    }

    @Override
    public JudgeResult check() {
        File file = new File(standardOutputPath);
        int length = userOutput.length;
        return new JudgeResult();
    }

    @Override
    public void compile() throws InterruptedException, IOException {
        Process exec = Runtime.getRuntime().exec(compileCmd + " Main.class", null, new File(workDir));
        if(!exec.waitFor(5, TimeUnit.SECONDS)){
            exec.destroy();
        }
    }

    @Override
    public void execute() throws IOException, InterruptedException {
        ProcessBuilder processBuilder
                = new ProcessBuilder(List.of(executeCmd,"Main"));
        processBuilder.directory(new File(workDir));
        processBuilder.redirectInput(new File(standardInputPath));
        Process process = processBuilder.start();
        if(!process.waitFor(5,TimeUnit.SECONDS)){
            process.destroy();
        }
        userOutput=process.getInputStream().readAllBytes();
    }
}
