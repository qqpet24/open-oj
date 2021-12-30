package com.xmu.other.controller;

import com.xmu.other.service.MailService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import request.SendMailDTO;

import javax.annotation.Resource;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailService mailService;

    @PostMapping
    @ApiOperation("发送一到多封邮件,如果失败，会返回失败邮件的序列号，如第3封失败返回2，如果传入三个列表不等长，取最短的")
    public Object sendMail(@RequestBody SendMailDTO sendMailDTO){
        return mailService.sendMail(sendMailDTO);
    }
}
