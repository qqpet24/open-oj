package com.xmu.auth.controller;

import com.xmu.auth.request.UserProfileVo;
import com.xmu.auth.request.UserVo;
import com.xmu.auth.service.UserService;
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
    @ApiOperation("登录")
    public Object login(@RequestBody UserVo user, HttpServletRequest request) {
        return userService.login(user, request);
    }

    @PostMapping("logout")
    @ApiOperation("登出")
    public Object logout(@RequestParam Long userId) {
        return null;
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    public Object register(@RequestBody UserVo userVo, HttpServletRequest request) {
        return userService.register(userVo, request);
    }

    @GetMapping("/exist")
    @ApiOperation("检查用户是否存在")
    public Object userExist(@RequestParam String username) {
        return userService.userExist(username);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}/role")
    @ApiOperation("修改用户角色")
    public Object modifyRoleOfUser(@PathVariable Long id) {
        return userService.modifyRoleOfUser(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{id}/info")
    @ApiOperation("获取用户信息")
    public Object getInfo(@PathVariable Long id) {
        //TODO:自己只能查自己的
        return userService.getInfo(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @PostMapping("/{id}/info")
    @ApiOperation("修改用户信息")
    public Object modifyUserInfo(@PathVariable Long id, @RequestBody UserProfileVo userProfileVo) {
        //TODO:自己只能改自己的
        return userService.modifyUserInfo(id, userProfileVo);
    }

    //有注解一定校验token
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/{id}/avatar")
    public Object getAvatarByUserId(@PathVariable Long id) {
        return userService.getAvatarByUserId(id);
    }

}
