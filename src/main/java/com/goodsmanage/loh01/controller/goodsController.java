package com.goodsmanage.loh01.controller;
// 包声明，控制器层包

// 导入统一响应结果类
// 导入商品实体类
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.entity.Goods;
import com.goodsmanage.loh01.entity.Result;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.service.goodsService;
// 导入商品服务接口
import lombok.extern.slf4j.Slf4j;
// 导入Lombok日志注解
// 导入SLF4J日志工厂类
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring自动装配注解
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
// 导入Spring Web GET映射注解
// 导入Spring Web请求映射注解
// 导入Spring Web REST控制器注解

// 导入List集合类


// 导入Java标准日志类
@Slf4j
// Lombok注解：自动生成日志对象log
@RestController
// Spring注解：标识这是一个REST控制器，返回JSON数据
public class goodsController {

    @Autowired
    private goodsService goodsService;
    @GetMapping("/listUser")
    public Result list(){
        List<Goods> list = goodsService.list();
        log.info("success");
        return Result.success(list);
    }
    @PostMapping("/saveUser")
    public boolean save(@RequestBody Goods goods){
        log.info("save user");
        return goodsService.save(goods);
    }
    @PostMapping("/modUser")
    public boolean mod(@RequestBody Goods goods){
        log.info("mod user");
        return goodsService.updateById(goods);
    }
    @PostMapping("/saveOrUpdateUser")
    public boolean saveOrUpdate(@RequestBody Goods goods){
        log.info("saveOrUpdate user");
        return goodsService.saveOrUpdate(goods);
    }
    @GetMapping("/deleteUser")
    public boolean delete(Integer id){
        log.info("delete user");
        return goodsService.removeById(id);
    }
    @PostMapping("/queryUser")
    public Page<Goods> query(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Goods goods) {
        // 创建分页对象
        Page<Goods> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件
        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(goods.getGoodsName()), Goods::getGoodsName, goods.getGoodsName());
        // 执行分页查询
        return goodsService.page(pageInfo, lambdaQueryWrapper);
    }
    @GetMapping("/pageUser")
    public Result getUserList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer total = goodsService.total();
        List<User> row = goodsService.row((page - 1) * pageSize, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", row);
        data.put("total", total);
        return Result.success(data);
    }
}