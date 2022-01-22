package com.xmu.problem.util;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public enum LanguageInfo {
    JAVA("java",0,List.of("javac"),List.of("java"),"Main.java","Main");

    private final String language;
    private final Integer code;
    private final List<String> compileCmd;
    private final List<String> executeCmd;
    private final String nameBeforeCompile;
    private final String nameBeforeExecute;


    LanguageInfo(String language, Integer code, List<String> compileCmd, List<String> executeCmd, String nameBeforeCompile, String nameBeforeExecute) {
        this.language = language;
        this.code = code;
        this.compileCmd = compileCmd;
        this.executeCmd = executeCmd;
        this.nameBeforeCompile = nameBeforeCompile;
        this.nameBeforeExecute = nameBeforeExecute;
    }

    public static LanguageInfo getInfoByLanguage(String language){
        for (LanguageInfo info: LanguageInfo.values()) {
            if(info.language.equals(language)){
                return info;
            }
        }
        return null;
    }

    public String compileCmd(){
        return String.format("%s %s",String.join(" ", this.compileCmd),nameBeforeCompile);
    }

    public List<String> executeCmd(){
        LinkedList<String> executeCmd = new LinkedList<>(this.executeCmd);
        executeCmd.add(nameBeforeExecute);
        return executeCmd;
    }

}

