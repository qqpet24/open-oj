package com.xmu.other.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.other.domain.Bulletin;
import com.xmu.other.mapper.BulletinMapper;
import com.xmu.other.service.BulletinService;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class BulletinServiceImpl extends ServiceImpl<BulletinMapper, Bulletin> implements BulletinService{
}
