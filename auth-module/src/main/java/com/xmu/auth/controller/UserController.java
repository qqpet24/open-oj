package com.xmu.auth.controller;

import com.xmu.auth.request.UserProfileVo;
import com.xmu.auth.request.UserVo;
import com.xmu.auth.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Anyone can't change the privileges of role include ROOT user
 *
 * @author summer
 * @see <a href=""></a><br/>
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("user login")
    public Object login(@RequestBody UserVo user, HttpServletRequest request) {
        return userService.login(user, request);
    }

    @PostMapping("logout")
    @ApiOperation("user logout")
    public Object logout(UserVo user) {
        return null;
    }

    @PostMapping("/register")
    @ApiOperation("user register")
    public Object register(@RequestBody UserVo userVo, HttpServletRequest request) {
        return userService.register(userVo, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{userId}/role")
    @ApiOperation("admin modify the user's role")
    public Object modifyRoleOfUser(@PathVariable Long userId) {
        return userService.modifyRoleOfUser(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{userId}/info")
    @ApiOperation("get user info")
    public Object getInfo(@PathVariable Long userId) {
        //TODO:自己只能查自己的
        return userService.getInfo(userId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("/{userId}/info")
    @ApiOperation("create or modify user info")
    public Object createOrModifyUserInfo(@PathVariable Long userId, @RequestBody UserProfileVo userProfileVo) {
        //TODO:自己只能改自己的
        return userService.createOrModifyUserInfo(userId, userProfileVo);
    }


}
