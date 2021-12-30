package com.xmu.other.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.other.domain.Bulletin;
import request.BulletinDTO;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface BulletinService extends IService<Bulletin> {
    Object getAllAnnouncement();

    Object getAnnouncementByIds(Long id);

    Object createOrModifyAnnouncement(BulletinDTO bulletinDTO);

    Object deleteAnnouncementById(Long id);
}
