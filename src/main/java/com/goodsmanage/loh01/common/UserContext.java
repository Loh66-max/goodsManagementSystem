package com.goodsmanage.loh01.common;

import com.goodsmanage.loh01.entity.User;

/**
 * 用户上下文类
 * 使用 ThreadLocal 存储当前登录用户信息
 */
public class UserContext {
    
    private static final ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    
    /**
     * 设置当前用户
     * 
     * @param user 用户对象
     */
    public static void setCurrentUser(User user) {
        userThreadLocal.set(user);
    }
    
    /**
     * 获取当前用户
     * 
     * @return 用户对象
     */
    public static User getCurrentUser() {
        return userThreadLocal.get();
    }
    
    /**
     * 清除当前用户（在请求结束后调用）
     */
    public static void clear() {
        userThreadLocal.remove();
    }
}

