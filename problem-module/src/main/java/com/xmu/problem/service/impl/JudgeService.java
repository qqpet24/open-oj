package com.xmu.problem.service.impl;

import org.jvnet.hk2.annotations.Service;
import org.springframework.scheduling.annotation.Async;

@Service
public class JudgeService {

    @Async("taskExecutor")
    public void Compile(String language,String code){

    }
}
