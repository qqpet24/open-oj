package com.xmu.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xmu.auth.domain.User;
import com.xmu.auth.request.UserProfileVo;
import com.xmu.auth.request.UserVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public interface UserService extends IService<User>{
    Object login(UserVo userVo,HttpServletRequest request);

    Object register(UserVo userVo, HttpServletRequest request);

    Object modifyRoleOfUser(Long userId);

    Object getInfo(Long userId);

    Object modifyUserInfo(Long userId, UserProfileVo userProfileVo);

    Object userExist(String username);

    Object getAvatarByUserId(Long id);
}
