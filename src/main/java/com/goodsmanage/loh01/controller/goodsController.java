package com.goodsmanage.loh01.controller;
// 包声明，控制器层包

import com.goodsmanage.loh01.pojo.Result;
// 导入统一响应结果类
import com.goodsmanage.loh01.pojo.goods;
// 导入商品实体类
import com.goodsmanage.loh01.pojo.goodsfrom;
import com.goodsmanage.loh01.pojo.page;
import com.goodsmanage.loh01.service.goodsService;
// 导入商品服务接口
import lombok.extern.slf4j.Slf4j;
// 导入Lombok日志注解
import org.slf4j.LoggerFactory;
// 导入SLF4J日志工厂类
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring自动装配注解
import org.springframework.web.bind.annotation.*;
// 导入Spring Web GET映射注解
// 导入Spring Web请求映射注解
// 导入Spring Web REST控制器注解

import java.util.List;
// 导入List集合类
import java.util.logging.Logger;


// 导入Java标准日志类
@Slf4j
// Lombok注解：自动生成日志对象log
@RestController
// Spring注解：标识这是一个REST控制器，返回JSON数据
public class goodsController {
    // 商品控制器类，公共访问修饰符
//    private static Logger log = (Logger) LoggerFactory.getLogger(goodsController.class);
    // 注释掉的日志对象创建代码，使用@Slf4j注解替代
//    @RequestMapping("/goods")
    // 注释掉的请求映射注解
    @Autowired
    // Spring注解：自动装配商品服务对象
    private goodsService goodsService;
    // 商品服务对象，私有访问修饰符

    @GetMapping("/getGoods")
    // Spring注解：处理GET请求，映射到/goods路径
    public Result getGoods(){
        // 查询商品列表的方法，返回统一响应结果
        List<goods> goodsList = goodsService.list();
        // 调用服务层方法获取商品列表
        log.info("获取全部货品信息成功");
        // 记录成功日志信息
        return Result.success(goodsList);
        // 返回成功响应，包含商品列表数据
    }
    @PostMapping("/createGoods")
    public Result createGoods(@RequestBody goods goods){
        goodsService.createGoods(goods);
        log.info("新增商品信息成功");
        return Result.success(goods);
    }
    @DeleteMapping("/deleteGoods/{id}")
    public Result deleteGoods(@PathVariable int id){
        goodsService.deleteGoods(id);
        log.info("删除商品信息成功");
        return Result.success();
    }
    @PutMapping("/goodsMod/{id}")
    public Result goodsMod(@PathVariable int id, @RequestBody goods goods){
        goodsService.goodsMod(id,goods);
        log.info("修改商品信息成功");
        return Result.success(goods);
    }
    @GetMapping("/getPageList")
    public Result getPageList(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10")Integer pageSize){
        Integer total = goodsService.total();

        List<goods> goodsList =goodsService.rows(page-1,pageSize);
        log.info("获取数据成功！数据总数为==="+total);
        return Result.success(goodsList);
    }


}