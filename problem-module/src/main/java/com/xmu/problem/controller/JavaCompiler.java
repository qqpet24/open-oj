package com.xmu.problem.controller;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public class JavaCompiler {

    private static final String compilerCmd = "javac ";
    private static final String executeCmd = "java ";
    private static final String className = "Main";

    /**
     * 编译
     */
    public static void compile(String path) {
        try {
            //编译
            Process exec = Runtime.getRuntime().exec("javac Main.java",
                    null,
                    new File(path));
            if (!exec.waitFor(5, TimeUnit.SECONDS)) {
                System.out.println("死循环");
                exec.destroy();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static byte[] execute(File inputCase) throws Exception {

        //运行
        ProcessBuilder processBuilder
                = new ProcessBuilder("java", "Main");
        processBuilder.directory(new File("C:\\Users\\Administrator\\Desktop\\user\\2\\code\\1"));
        processBuilder.redirectInput(inputCase);
        Process process = processBuilder.start();
        //if (process.getErrorStream() != null) {
        //    throw new Exception(process.getErrorStream().toString());
        //}
        try {
            if (!process.waitFor(5, TimeUnit.SECONDS)) {
                System.out.println("死循环");
                process.destroy();
            }
            return process.getInputStream().readAllBytes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //超时
        //process.destroy();
        return null;
    }

    public static Boolean check(File solution, byte[] userInput) {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(solution));
            byte[] bytes = bufferedInputStream.readAllBytes();
            int i=0,j=0;
            while (i<bytes.length&&j<userInput.length) {
                if(bytes[i] == '\r'){
                    i++;
                    continue;
                }
                if(userInput[j] == '\r'){
                    j++;
                    continue;
                }
                if(bytes[i]!=userInput[j]){
                    return false;
                }
                i++;
                j++;
                if(i==bytes.length&&j== userInput.length){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}
