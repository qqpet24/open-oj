package com.xmu.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
public class Jwt {

    private static final String SECRET = "OnlineJudgeSECRETOnlineJudgeOnlineJudgeSECRETOnlineJudgeOnlineJudgeSECRETOnlineJudgeOnlineJudgeSECRETOnlineJudgeOnlineJudgeSECRETOnlineJudge";

    private static Key signKey() {
        byte[] decode = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(decode);
    }

    /**
     * Method: Create A Token With Claims & Expired Data
     *
     * @param claims     Token Claims
     * @param expiration Token Expired Data
     * @return Token Str
     */
    public static String createToken(Map<String, Object> claims, Date expiration) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expiration)
                .signWith(signKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Method: Create A Token With Claims
     *
     * @param claims Token Claims
     * @return Token Str
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(signKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    /**
     * Method: Get Claims From Token
     *
     * @param token Token Str
     * @return Token Claims
     */
    public static Claims getClaimsFromToke(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(signKey())
                    .build()
                    .parseClaimsJws(token).getBody();
            //TODO:统一的异常处理类
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * Get User Id From Token
     *
     * @param token Token Str
     * @return User Id
     */
    public static Long getUserIdFromToken(String token) {
        return getClaimsFromToke(token).get("userId", Long.class);
    }

    /**
     * Get Username From Token
     *
     * @param token Token Str
     * @return Username
     */
    public static String getUserNameFromToken(String token) {
        return getClaimsFromToke(token).get("username", String.class);
    }

}
