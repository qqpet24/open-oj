package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.problem.domain.CustomInput;
import com.xmu.problem.mapper.CustomInputMapper;
import com.xmu.problem.service.CustomInputService;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class CustomInputServiceImpl extends ServiceImpl<CustomInputMapper, CustomInput> implements CustomInputService {
}
