package com.goodsmanage.loh01.common;

import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 拦截器
 * 用于验证请求中的 JWT token
 */
@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 处理 OPTIONS 预检请求
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        
        // 获取请求头中的 token
        String token = request.getHeader("Authorization");
        
        // 如果请求头中没有 token，尝试从参数中获取
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }
        
        // 如果还是没有 token，返回未授权错误
        if (token == null || token.isEmpty()) {
            log.warn("请求缺少 JWT token: {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"未授权，请先登录\",\"data\":null}");
            return false;
        }
        
        // 移除 "Bearer " 前缀（如果存在）
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        // 验证 token
        if (!JwtUtil.validateToken(token)) {
            log.warn("JWT token 无效或已过期: {}", request.getRequestURI());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"token 无效或已过期\",\"data\":null}");
            return false;
        }
        
        // 解析 token 并设置用户信息到上下文
        Claims claims = JwtUtil.parseToken(token);
        if (claims != null) {
            User user = new User();
            Object userId = claims.get("userId");
            if (userId instanceof Integer) {
                user.setId((Integer) userId);
            } else if (userId instanceof Number) {
                user.setId(((Number) userId).intValue());
            }
            user.setNum(claims.get("username", String.class));
            user.setRole(claims.get("role", String.class));
            user.setValue(claims.get("value", String.class));
            
            // 将用户信息存储到 ThreadLocal
            UserContext.setCurrentUser(user);
            log.debug("JWT token 验证成功，用户: {}", user.getNum());
        }
        
        return true;
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求结束后清除 ThreadLocal，防止内存泄漏
        UserContext.clear();
    }
}

