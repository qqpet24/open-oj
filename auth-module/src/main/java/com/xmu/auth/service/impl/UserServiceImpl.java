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
import com.xmu.auth.response.UserBriefDTO;
import com.xmu.auth.service.UserProfileService;
import com.xmu.auth.service.UserService;
import com.xmu.common.enums.ResponseCode;
import com.xmu.common.enums.Role;
import com.xmu.common.utils.IpUtil;
import com.xmu.common.utils.Jwt;
import com.xmu.common.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
                    .setAccessTime(LocalDateTime.now());
            if (!updateById(user)) {
                return Response.of(ResponseCode.INTERNAL_SERVER_ERROR).entity(INTERNAL_SERVER_ERROR);
            }
            String token = Jwt.createToken(Map.of(
                    "username", userDetails.getUsername(),
                    "userId", userDetails.getId()
            ));
            return Response.of(ResponseCode.OK, Map.of("Authorization", token, "userId", user.getId(), "role", user.getRoleId())).entity(OK);
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
                .setRegisterTime(LocalDateTime.now())
                .setAccessTime(LocalDateTime.now())
                //.setDefunct(Defunct.NOT_DEFUNCT.getCode())
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
    public Object modifyRoleOfUser(Long userId, String role) {
        User user = getById(userId);
        switch (role) {
            case "common" -> user.setRoleId(2L);
            case "admin" -> user.setRoleId(1L);
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
        User user;
        if ((user = getById(userId)) == null) {
            return Response.of(ResponseCode.USER_NOT_EXIST).entity(BAD_REQUEST);
        }
        return Response.of(ResponseCode.OK, user.profile()).entity(OK);
    }

    /**
     * Create or Modify User Info
     *
     * @return Ok or Error
     * @see UserProfileVo
     */
    @Override
    public Object modifyUserInfo(User user) {
        String token = Jwt.createToken(Map.of("username", user.getUsername(), "userId", user.getId()));
        return this.updateById(user) ? Response.of(ResponseCode.OK, Map.of("Authorization", token, "userId", user.getId())) : Response.of(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Object userExist(String username) {
        if (this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username)) != null) {
            return Response.of(ResponseCode.OK, true).entity(OK);
        }
        return Response.of(ResponseCode.OK, false).entity(OK);
    }

    @Override
    public Object getAvatarByUserId(Long id) {
        String avatarUrl = this.getById(id).getAvatar();
        return Response.of(ResponseCode.OK,
                Map.of("avatarUrl", Objects.requireNonNullElse(avatarUrl, "https://joeschmoe.io/api/v1/random"))).entity(OK);
    }

    @Override
    public Object getUsers() {
        List<UserBriefDTO> users = this.list(Wrappers.lambdaQuery()).stream().map(User::brief).collect(Collectors.toList());
        return Response.of(ResponseCode.OK, users);
    }

    @Override
    public Object searchUserByName(String name) {
        List<UserBriefDTO> users = this.list(Wrappers.<User>lambdaQuery().like(User::getUsername, name)).stream().map(User::brief).collect(Collectors.toList());
        return Response.of(ResponseCode.OK, users);
    }

    @Override
    public Object changeRoleById(Long id, Integer role) {
        User user = this.getById(id);
        User updateUser = user.setRoleId(Long.valueOf(role));
        this.updateById(updateUser);
        return Response.of(ResponseCode.OK);
    }

}
