package com.xmu.auth.config.security;

import com.xmu.common.utils.Jwt;
import lombok.extern.slf4j.Slf4j;
import nonapi.io.github.classgraph.json.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Component
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Value("${jwt.header:Authorization}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String token=request.getHeader(tokenHeader);
        if(token!=null){
            String username= Jwt.getUserNameFromToken(token);
            log.info("check authentication with username: "+username);
            if(username!=null&& SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails
                        = userDetailsService.loadUserByUsername(username);
                if(validateToken(token, (JwtUser) userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    /**
     * Check username & password
     * @param token token Str
     * @param jwtUser User Info
     * @return changed(FALSE) or not(TRUE)
     */
    private Boolean validateToken(String token, JwtUser jwtUser){
        return jwtUser.getUsername().equals(Jwt.getUserNameFromToken(token)) &&jwtUser.getId().equals(Jwt.getUserIdFromToken(token));
    }
}
