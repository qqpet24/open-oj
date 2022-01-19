package com.xmu.other.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.utils.Response;
import com.xmu.other.domain.Bulletin;
import com.xmu.other.mapper.BulletinMapper;
import com.xmu.other.service.BulletinService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import request.BulletinDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class BulletinServiceImpl extends ServiceImpl<BulletinMapper, Bulletin> implements BulletinService{
    @Override
    public Object getAllAnnouncement() {
        List<com.xmu.other.response.BulletinDTO> announcements
                = this.list(Wrappers.lambdaQuery()).stream().map(Bulletin::toDTO).collect(Collectors.toList());
        return Response.of(ResponseCode.OK,announcements);
    }

    @Override
    public Object getAnnouncementByIds(Long id) {
        Bulletin bulletin = this.getById(id);
        if(bulletin!=null){
            com.xmu.other.response.BulletinDTO bulletinDTO = new com.xmu.other.response.BulletinDTO();
            BeanUtils.copyProperties(bulletin,bulletinDTO);
            bulletinDTO.setTime(bulletin.getTime().toString());
            return Response.of(ResponseCode.OK,bulletinDTO);
        }else{
            return Response.of(ResponseCode.ANNOUNCEMENT_NOT_EXIST);
        }
    }

    @Override
    public Object createOrModifyAnnouncement(BulletinDTO bulletinDTO) {
        Bulletin bulletin = new Bulletin();
        BeanUtils.copyProperties(bulletinDTO,bulletin);
        boolean result = this.saveOrUpdate(bulletin);

        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Object deleteAnnouncementById(Long id) {
        boolean result = this.removeById(id);
        if(result){
            return Response.of(ResponseCode.OK);
        }else{
            return Response.of(ResponseCode.ANNOUNCEMENT_NOT_EXIST);
        }
    }
}
