package com.xmu.other.controller;

import com.xmu.other.service.BulletinService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import request.BulletinDTO;

/**
 * @author qqpet24
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping("/bulletin")
public class BulletinController {
    @Autowired
    BulletinService bulletinService;

    @GetMapping("/all")
    @ApiOperation("获取全部公告")
    public Object getAllAnnouncement(){
        return bulletinService.getAllAnnouncement();
    }
    @GetMapping("/{id}")
    @ApiOperation("获取公告详情")
    public Object getAnnouncementByIds(@PathVariable Long id){
        return bulletinService.getAnnouncementByIds(id);
    }
    @PostMapping
    @ApiOperation("新增或者修改公告")
    public Object createOrModifyAnnouncement(@RequestBody BulletinDTO bulletinDTO){
        return bulletinService.createOrModifyAnnouncement(bulletinDTO);
    }
    @DeleteMapping("/{id}")
    @ApiOperation("删除公告")
    public Object deleteAnnouncementById(@PathVariable Long id){
        return bulletinService.deleteAnnouncementById(id);
    }
}
