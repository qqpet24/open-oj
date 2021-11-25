package com.xmu.auth.config.security;

import com.xmu.auth.domain.Role;
import com.xmu.auth.domain.User;
import com.xmu.auth.service.RoleService;
import com.xmu.common.utils.SpringContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public final class JwtUserFactory {

    private JwtUserFactory() {

    }

    public static JwtUser create(User user) {
        return JwtUser.of(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities(user.getRoleId())
        );
    }

    private static List<GrantedAuthority> authorities(Long roleId) {
        RoleService roleService = SpringContextHolder.getBean(RoleService.class);
        Role role = roleService.getById(roleId);
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }
}
