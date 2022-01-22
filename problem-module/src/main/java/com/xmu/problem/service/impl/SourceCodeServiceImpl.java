package com.xmu.problem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.problem.domain.SourceCode;
import com.xmu.problem.mapper.SourceCodeMapper;
import com.xmu.problem.service.SourceCodeService;
import org.springframework.stereotype.Service;

@Service
public class SourceCodeServiceImpl extends ServiceImpl<SourceCodeMapper, SourceCode> implements SourceCodeService {
}
