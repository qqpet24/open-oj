package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.problem.domain.RuntimeInfo;
import com.xmu.problem.mapper.RuntimeInfoMapper;
import com.xmu.problem.service.RuntimeInfoService;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class RuntimeInfoServiceImpl extends ServiceImpl<RuntimeInfoMapper, RuntimeInfo> implements RuntimeInfoService {
}
