package com.goodsmanage.loh01.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.entity.Result;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.entity.page;
import com.goodsmanage.loh01.service.userService;
import com.goodsmanage.loh01.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class userController {
    @Autowired
    private userService userservice;

    @GetMapping("/listUser")
    public Result list() {
        List<User> list = userservice.list();
        log.info("success");
        return Result.success(list);
    }

    @PostMapping("/saveUser")
    public boolean save(@RequestBody User user) {
        log.info("save user");
        return userservice.save(user);
    }

    @PostMapping("/modUser")
    public boolean mod(@RequestBody User user) {
        log.info("mod user");
        return userservice.updateById(user);
    }

    @PostMapping("/saveOrUpdateUser")
    public boolean saveOrUpdate(@RequestBody User user) {
        log.info("saveOrUpdate user");
        return userservice.saveOrUpdate(user);
    }

    @GetMapping("/deleteUser")
    public boolean delete(Integer id) {
        log.info("delete user");
        return userservice.removeById(id);
    }

    @PostMapping("/queryUser")
    public Page<User> query(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody User user) {
        // 创建分页对象
        Page<User> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(user.getNum()), User::getNum, user.getNum());
        // 执行分页查询
        return userservice.page(pageInfo, lambdaQueryWrapper);
    }

    @PostMapping("/login")
    public ResponseEntity<Result> login(@RequestBody User user) {
        try {
            if (StringUtils.isBlank(user.getNum())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Result.error("用户名不能为空"));
            }

            if (StringUtils.isBlank(user.getPassword())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(Result.error("密码不能为空"));
            }

            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(StringUtils.isNotBlank(user.getNum()), User::getNum, user.getNum())
                    .eq(StringUtils.isNotBlank(user.getPassword()), User::getPassword, user.getPassword())
                    .eq(StringUtils.isNotBlank(user.getRole()), User::getRole, user.getRole());

            User loginUser = userservice.getOne(wrapper, true);

            if (loginUser == null) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Result.error("用户名或密码错误"));
            }

            if ("1".equals(loginUser.getValue())) {
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(Result.error("账号未激活，请联系管理员"));
            }

            // 根据角色编码（0-超级管理员；1-管理员；2-普通用户）计算权限
            // 约定：
            // - 超级管理员(0)：可以进行用户管理 + 货品管理 + 其他全部功能
            // - 管理员(1)：不能进行用户管理，但可以进行货品管理及其他功能
            // - 普通用户(2)：不能进行用户管理和货品管理，只能使用查询等普通功能
            String role = loginUser.getRole();
            boolean canManageUser = "0".equals(role);
            boolean canManageGoods = "0".equals(role) || "1".equals(role);

            Map<String, Boolean> permission = new HashMap<>();
            permission.put("canManageUser", canManageUser);
            permission.put("canManageGoods", canManageGoods);

            // 生成 JWT token
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", loginUser.getId());
            claims.put("username", loginUser.getNum());
            claims.put("role", loginUser.getRole());
            claims.put("value", loginUser.getValue());
            String token = JwtUtil.generateToken(claims);

            // 构建返回数据
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("id", loginUser.getId());
            userInfo.put("num", loginUser.getNum());
            userInfo.put("role", loginUser.getRole());
            userInfo.put("value", loginUser.getValue());
            userInfo.put("permission", permission);
            userInfo.put("token", token); // 将 token 返回给前端

            log.info("用户登录成功: {}, 角色: {}, 权限: canManageUser={}, canManageGoods={}", 
                    loginUser.getNum(), role, canManageUser, canManageGoods);

            return ResponseEntity.ok(Result.success(userInfo));

        } catch (Exception e) {
            log.error("登录异常", e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("服务器异常"));
        }
    }



    @GetMapping("/pageUser")
    public Result getUserList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer total = userservice.total();
        List<User> row = userservice.row((page - 1) * pageSize, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", row);
        data.put("total", total);
        return Result.success(data);
    }
}