package com.xmu.other.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/other")
public class OtherController {

    @GetMapping("/test")
    public Object test(){
        return null;
    }
}
