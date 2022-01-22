package com.xmu.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;


/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@SpringBootApplication(scanBasePackages = {"com.xmu.*"})
@MapperScan({"com.xmu.*.mapper"})
public class OpenOjApplication {
    public static void main(String[] args) {
        SpringApplication.run(OpenOjApplication.class,args);
    }
}
