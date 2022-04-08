package com.xmu.problem.request.util;

import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

@Getter
public enum LanguageInfo {
    //这里面语言code只能加不能改,language都改成开头大写
    JAVA("java", 0, "javac Main.java", List.of("java", "Main"), "Main.java", "Main", 0d, 1d, 1d),
    //g++ Main.cc -o Main -O2 -lm -std=c++14
    CPP("cpp", 1, "g++ Main.cpp -o Main -O2 -lm -std=c++14 -w -DONLINE_JUDGE", List.of("./Main"), "Main.cpp", "Main", 0d, 1d, 1d),
    //gcc Main.c -o Main -O2 -lm -std=c99
    C("c", 2, "gcc Main.c -o Main -O2 -lm -std=c99 -w -DONLINE_JUDGE -nostdinc", List.of("./Main"), "Main.c", "Main", 0d, 1d, 1d);
    private final String language;
    private final Integer code;
    private final String compileCmd;
    private final List<String> executeCmd;
    private final String nameBeforeCompile;
    private final String nameBeforeExecute;
    private final Double languageTimeBasis;
    private final Double languageTimeMultiplier;
    private final Double machineMultiplier;


    LanguageInfo(String language, Integer code, String compileCmd, List<String> executeCmd, String nameBeforeCompile, String nameBeforeExecute,
                 Double languageTimeBasis, Double languageTimeMultiplier, Double machineMultiplier) {
        this.language = language;
        this.code = code;
        this.compileCmd = compileCmd;
        this.executeCmd = executeCmd;
        this.nameBeforeCompile = nameBeforeCompile;
        this.nameBeforeExecute = nameBeforeExecute;
        this.languageTimeBasis = languageTimeBasis;
        this.languageTimeMultiplier = languageTimeMultiplier;
        this.machineMultiplier = machineMultiplier;
    }

    public static LanguageInfo getInfoByLanguage(String language) {
        for (LanguageInfo info : LanguageInfo.values()) {
            if (info.language.equals(language)) {
                return info;
            }
        }
        return null;
    }

    public Double getRealTimeLimit(Double timeLimit) {
        return machineMultiplier * (timeLimit * languageTimeMultiplier + languageTimeBasis);
    }

    public String compileCmd() {
        return String.format("%s", String.join(" ", this.compileCmd));
        //return String.format("%s %s",String.join(" ", this.compileCmd),nameBeforeCompile);
    }

    public List<String> executeCmd() {
        LinkedList<String> executeCmd = new LinkedList<>(this.executeCmd);
        //executeCmd.add(nameBeforeExecute);
        return executeCmd;
    }

}

