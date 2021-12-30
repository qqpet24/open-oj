package com.xmu.other.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.other.domain.MailRecord;
import request.SendMailDTO;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
public interface MailService extends IService<MailRecord> {
    Object sendMail(SendMailDTO sendMailDTO);
}
