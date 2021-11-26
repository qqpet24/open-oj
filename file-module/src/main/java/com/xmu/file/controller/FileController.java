package com.xmu.file.controller;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.upload.FastFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Jwt;
import com.xmu.common.utils.Response;
import com.xmu.file.domain.File;
import com.xmu.file.service.FileService;
import io.swagger.annotations.ApiOperation;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;


/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FastFileStorageClient storageClient;

    @Autowired
    private FileService fileService;

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("/upload")
    @ApiOperation("file upload")
    public Object upload(@RequestBody  MultipartFile file, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        String username = Jwt.getUserNameFromToken(token);
        Long userId = Jwt.getUserIdFromToken(token);
        try {
            FastFile.Builder builder = new FastFile.Builder();
            String filename = file.getOriginalFilename();
            String extName = FilenameUtils.getExtension(filename);
            builder.withFile(
                    file.getInputStream(),
                    file.getSize(),
                    extName);
            //TODO:如果是图片类型，则放如img group
            StorePath storePath = storageClient.uploadFile(builder.build());
            String path = storePath.getFullPath();
            boolean saved = fileService.save(File.of(
                    null,
                    filename,
                    userId,
                    (path="101.37.20.199:8888/"+path),
                    extName,
                    username,
                    new Date()));
            if (saved) {
                return Response.of(ResponseCode.OK, Map.of("path",path)).entity(OK);
            }
        } catch (IOException e) {
            return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).setMsg(e.getMessage()).entity(INTERNAL_SERVER_ERROR);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
    }

}
