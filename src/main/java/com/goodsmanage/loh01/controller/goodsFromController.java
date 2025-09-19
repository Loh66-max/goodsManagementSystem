package com.goodsmanage.loh01.controller;
// 包声明，控制器层包

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.entity.Goodsfrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.goodsmanage.loh01.service.goodsFromService;
import com.goodsmanage.loh01.entity.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 导入Spring Web REST控制器注解
@Slf4j
@RestController
// Spring注解：标识这是一个REST控制器
public class goodsFromController {
    // 商品来源控制器类，公共访问修饰符
    @Autowired
    //这行代码为了能够私有访问到Service
    private goodsFromService goodsFromService;
    @GetMapping("/listUser")
    public Result list(){
        List<Goodsfrom> list = goodsFromService.list();
        log.info("success");
        return Result.success(list);
    }
    @PostMapping("/saveUser")
    public boolean save(@RequestBody Goodsfrom goodsfrom){
        log.info("save user");
        return goodsFromService.save(goodsfrom);
    }
    @PostMapping("/modUser")
    public boolean mod(@RequestBody Goodsfrom goodsfrom){
        log.info("mod user");
        return goodsFromService.updateById(goodsfrom);
    }
    @PostMapping("/saveOrUpdateUser")
    public boolean saveOrUpdate(@RequestBody Goodsfrom goodsfrom){
        log.info("saveOrUpdate user");
        return goodsFromService.saveOrUpdate(goodsfrom);
    }
    @GetMapping("/deleteUser")
    public boolean delete(Integer id){
        log.info("delete user");
        return goodsFromService.removeById(id);
    }
    @PostMapping("/queryUser")
    public Page<Goodsfrom> query(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Goodsfrom goodsfrom) {
        // 创建分页对象
        Page<Goodsfrom> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件
        LambdaQueryWrapper<Goodsfrom> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(goodsfrom.getName()), Goodsfrom::getName, goodsfrom.getName());
        // 执行分页查询
        return goodsFromService.page(pageInfo, lambdaQueryWrapper);
    }
    @GetMapping("/pageUser")
    public Result getUserList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer total = goodsFromService.total();
        List<User> row = goodsFromService.row((page - 1) * pageSize, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", row);
        data.put("total", total);
        return Result.success(data);
    }


}