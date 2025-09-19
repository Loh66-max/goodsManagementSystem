package com.goodsmanage.loh01.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.entity.Result;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.entity.page;
import com.goodsmanage.loh01.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result list(){
        List<User> list = userservice.list();
        log.info("success");
        return Result.success(list);
    }
    @PostMapping("/saveUser")
    public boolean save(@RequestBody User user){
        log.info("save user");
        return userservice.save(user);
    }
    @PostMapping("/modUser")
    public boolean mod(@RequestBody User user){
        log.info("mod user");
        return userservice.updateById(user);
    }
    @PostMapping("/saveOrUpdateUser")
    public boolean saveOrUpdate(@RequestBody User user){
        log.info("saveOrUpdate user");
        return userservice.saveOrUpdate(user);
    }
    @GetMapping("/deleteUser")
    public boolean delete(Integer id){
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