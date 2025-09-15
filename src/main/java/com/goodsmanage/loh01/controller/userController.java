package com.goodsmanage.loh01.controller;

import com.goodsmanage.loh01.pojo.Result;
import com.goodsmanage.loh01.pojo.page;
import com.goodsmanage.loh01.pojo.user;
import com.goodsmanage.loh01.service.userService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class userController {
    @Qualifier("userServiceImpl")
    @Autowired
    private userService userservice;
    @GetMapping("/getUser")
    public Result result(){
        List<user> list = userservice.list();
        log.info("获取用户信息成功");
        return Result.success(list);
    }
    @DeleteMapping("/userDelete/{id}")
    public Result delete(@PathVariable Integer id){
        userservice.delete(id);
        log.info("根据ID删除{}成功",id);
        return Result.success();
    }
    @PostMapping("/userCreate")
    public Result create(@RequestBody user user){
        userservice.create(user);
        log.info("添加成功");
        return Result.success(user);
    }
    @PutMapping("/userMod/{id}")
    public Result mod(@PathVariable Integer id, @RequestBody user user){
        userservice.mod(id,user);
        log.info("修改成功");
        return Result.success(user);
    }

    @GetMapping("/getUserList")
    public Result getUserList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer total = userservice.total();
        List<user> row = userservice.row((page - 1) * pageSize, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", row);
        data.put("total", total);
        return Result.success(data);
    }
}