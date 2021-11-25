//package com.xmu.auth;
//
//import com.xmu.auth.domain.Role;
//import com.xmu.auth.service.impl.RoleServiceImpl;
//import com.xmu.common.utils.Jwt;
//import io.jsonwebtoken.Claims;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.Map;
//
//@SpringBootTest
//class OjApplicationTests {
//
//    @Resource
//    private RoleServiceImpl roleService;
//
//    @Test
//    void contextLoads() {
//
//    }
//
//    @Test
//    void createToken(){
//        System.out.println(Jwt.createToken(Map.of("username","summer"), new Date()));
//    }
//
//    @Test
//    void getClaimsFormToken(){
//        Map<String, Object> user = Map.of("username", "summer", "password", "summer521yu");
//        Date expiredDate = new Date();
//        expiredDate.setTime(expiredDate.getTime()+43200000);
//        String token = Jwt.createToken(user,expiredDate);
//        Claims claims = Jwt.getClaimsFromToke(token);
//        String username = claims.get("username", String.class);
//        String password = claims.get("password", String.class);
//        Assertions.assertEquals(user.get("username"),username);
//        Assertions.assertEquals(user.get("password"),password);
//    }
//
//    @Test
//    void mysqlJsonTest(){
//        Role role = roleService.getById(1L);
//        System.out.println(role);
//    }
//
//}
