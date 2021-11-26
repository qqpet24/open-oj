package com.xmu.auth.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xmu.auth.config.security.JwtUser;
import com.xmu.auth.config.security.service.JwtUserDetailService;
import com.xmu.auth.domain.User;
import com.xmu.auth.domain.UserProfile;
import com.xmu.auth.mapper.UserMapper;
import com.xmu.auth.request.UserProfileVo;
import com.xmu.auth.request.UserVo;
import com.xmu.auth.service.UserProfileService;
import com.xmu.auth.service.UserService;
import com.xmu.common.utils.IpUtil;
import com.xmu.common.utils.Jwt;
import com.xmu.common.utils.Response;
import com.xmu.common.enums.Defunct;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailService userDetailsService;

    @Autowired
    private UserProfileService userProfileService;

    /**
     * login
     *
     * @param userVo  username&password
     * @param request For User Ip
     * @return Token Str
     */
    @Override
    public Object login(UserVo userVo, HttpServletRequest request) {
        //spring security
        UsernamePasswordAuthenticationToken upToken
                = new UsernamePasswordAuthenticationToken(userVo.getUsername(), userVo.getPassword());
        Authentication authentication
                = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        JwtUser userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(userVo.getUsername());
        } catch (UsernameNotFoundException e) {
            return Response.of(ResponseCode.USERNAME_OR_PASSWORD_ERROR).entity(BAD_REQUEST);
        }
        //spring security
        if (userDetails != null) {
            User user = getById(userDetails.getId())
                    .setIp(IpUtil.getIpAddress(request))
                    .setAccessTime(new Date());
            if (!updateById(user)) {
                return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
            }
            String token = Jwt.createToken(Map.of(
                    "username", userDetails.getUsername(),
                    "userId", userDetails.getId()
            ));
            return Response.of(ResponseCode.OK, Map.of("Authorization", token, "userId", user.getId())).entity(OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
    }

    /**
     * User Register
     *
     * @param userVo  username&&password
     * @param request For User IpAddress
     * @return OK or Error
     */
    @Override
    public Object register(UserVo userVo, HttpServletRequest request) {
        String userIp = IpUtil.getIpAddress(request);
        String username = userVo.getUsername();
        if (getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username)) != null) {
            return Response.of(ResponseCode.USER_EXIST).entity(BAD_REQUEST);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPass = userVo.getPassword();
        User user = userVo.User()
                .setPassword(encoder.encode(rawPass))
                .setNickname(username)
                .setRegisterTime(new Date())
                .setAccessTime(new Date())
                .setDefunct(Defunct.NOT_DEFUNCT.getCode())
                .setRoleId(Role.USER.getRoleId())
                .setIp(userIp);
        if (save(user)) {
            return Response.of(ResponseCode.OK).entity(OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
    }

    /**
     * common user -> admin or admin -> common user
     *
     * @param userId User Id
     * @return TRUE or FALSE
     */
    @Override
    public Object modifyRoleOfUser(Long userId) {
        User user = getById(userId);
        if (user.getRoleId().equals(Role.ADMIN.getRoleId())) {
            user.setRoleId(Role.USER.getRoleId());
        } else {
            user.setRoleId(Role.ADMIN.getRoleId());
        }
        if (updateById(user)) {
            return Response.of(ResponseCode.OK).entity(OK);
        }
        return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
    }

    /**
     * Get User Info By User Id
     *
     * @param userId User Id
     * @return User
     * @see UserProfile
     */
    @Override
    public Object getInfo(Long userId) {
        if (getById(userId) == null) {
            return Response.of(ResponseCode.USER_NOT_EXIST).entity(BAD_REQUEST);
        }
        UserProfile userProfile;
        if ((userProfile = userProfileService.getOne(
                Wrappers.<UserProfile>lambdaQuery().eq(UserProfile::getUserId, userId)))
                == null) {
            return Response.of(ResponseCode.EMPTY_USER_INFO).entity(OK);
        }
        return Response.of(ResponseCode.OK, userProfile).entity(OK);
    }

    /**
     * Create or Modify User Info
     *
     * @param userId        User Id
     * @param userProfileVo UserProfileVo
     * @return Ok or Error
     * @see UserProfileVo
     */
    @Override
    public Object modifyUserInfo(Long userId, UserProfileVo userProfileVo) {
        if (getById(userId) == null) {
            return Response.of(ResponseCode.USER_NOT_EXIST).entity(BAD_REQUEST);
        }
        UserProfile userProfile = userProfileService.getOne(
                Wrappers.<UserProfile>lambdaQuery().eq(UserProfile::getUserId, userId));
        try {
            if (userProfile == null) {
                userProfile = userProfileVo.userProfile();
                userProfileService.save(userProfile);
            } else {
                userProfileVo.userProfile(userProfile);
                userProfileService.updateById(userProfile);
            }
            return Response.of(ResponseCode.OK).entity(OK);
        } catch (Exception e) {
            return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
        }
    }

}
