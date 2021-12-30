package com.xmu.other.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.other.domain.MailRecord;
import com.xmu.other.mapper.MailRecordMapper;
import com.xmu.other.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import request.SendMailDTO;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class MailServiceImpl extends ServiceImpl<MailRecordMapper, MailRecord> implements MailService{
    @Resource
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailUserName;

    @Override
    public Object sendMail(SendMailDTO sendMailDTO) {
        int size = Math.min(sendMailDTO.getTextList().size(),Math.min(sendMailDTO.getToList().size(),sendMailDTO.getSubjectList().size()));
        List<Integer> mailFailed = new LinkedList<>();

        for(int i = 0;i<size;i++){
            MailRecord mailRecord = new MailRecord();
            try{
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

                simpleMailMessage.setFrom(mailUserName);
                simpleMailMessage.setTo(sendMailDTO.getToList().get(i));
                simpleMailMessage.setSubject(sendMailDTO.getSubjectList().get(i));
                simpleMailMessage.setText(sendMailDTO.getTextList().get(i));

                mailRecord.setSubjectValue(sendMailDTO.getSubjectList().get(i));
                mailRecord.setTextValue(sendMailDTO.getTextList().get(i));
                mailRecord.setToValue(sendMailDTO.getToList().get(i));
                mailRecord.setFromValue(mailUserName);
                mailRecord.setTimeValue(LocalDateTime.now());
                javaMailSender.send(simpleMailMessage);
                mailRecord.setStatusValue(0);
            }catch (Exception e){
                mailRecord.setStatusValue(1);
                mailFailed.add(i);
            }
            this.save(mailRecord);
        }

        if(mailFailed.size()==0){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.SEND_MAIL_PARTIAL_OR_ALL_FAILED,mailFailed);
        }
    }
}
