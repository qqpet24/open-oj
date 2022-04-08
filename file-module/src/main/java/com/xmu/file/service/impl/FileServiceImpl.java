package com.xmu.file.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.file.domain.MyFile;
import com.xmu.file.mapper.FileMapper;
import com.xmu.file.service.FileService;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, MyFile> implements FileService {
}
