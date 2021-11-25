package com.xmu.auth.config.security.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xmu.auth.config.security.JwtUser;
import com.xmu.auth.config.security.JwtUserFactory;
import com.xmu.auth.domain.User;
import com.xmu.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public JwtUser loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user
                = userService.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        //TODO:检查用户是否被ban
        if(user==null){
            throw new UsernameNotFoundException("No user found with username: "+username);
        }
        return JwtUserFactory.create(user);
    }
}
