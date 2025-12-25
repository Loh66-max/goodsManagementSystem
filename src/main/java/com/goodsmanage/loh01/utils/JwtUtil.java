package com.goodsmanage.loh01.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 * 用于生成和解析 JWT token
 */
@Slf4j
public class JwtUtil {
    
    // JWT 密钥（实际项目中应该从配置文件读取，且长度至少 256 位）
    private static final String SECRET_KEY = "goodsManagementSystemSecretKeyForJWTTokenGeneration2024";
    
    // Token 过期时间：7天（单位：毫秒）
    private static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;
    
    /**
     * 获取签名密钥
     */
    private static SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }
    
    /**
     * 生成 JWT token
     * 
     * @param claims 要包含在 token 中的信息（如用户ID、用户名、角色等）
     * @return JWT token 字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        
        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }
    
    /**
     * 从 token 中解析 Claims
     * 
     * @param token JWT token
     * @return Claims 对象
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("解析 JWT token 失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 验证 token 是否有效
     * 
     * @param token JWT token
     * @return true 如果 token 有效，false 否则
     */
    public static boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return false;
            }
            // 检查是否过期
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("验证 JWT token 失败: {}", e.getMessage());
            return false;
        }
    }
    
    /**
     * 从 token 中获取用户ID
     * 
     * @param token JWT token
     * @return 用户ID
     */
    public static Integer getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            Object userId = claims.get("userId");
            if (userId instanceof Integer) {
                return (Integer) userId;
            } else if (userId instanceof Number) {
                return ((Number) userId).intValue();
            }
        }
        return null;
    }
    
    /**
     * 从 token 中获取用户名
     * 
     * @param token JWT token
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.get("username", String.class);
        }
        return null;
    }
    
    /**
     * 从 token 中获取角色
     * 
     * @param token JWT token
     * @return 角色
     */
    public static String getRoleFromToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.get("role", String.class);
        }
        return null;
    }
}

