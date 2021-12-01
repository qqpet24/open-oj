package com.xmu.other.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.other.domain.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Mapper
public interface MessageService extends IService<Message> {
}
