package com.goodsmanage.loh01.controller;
// 包声明，控制器层包

import com.goodsmanage.loh01.pojo.goodsfrom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.goodsmanage.loh01.service.goodsFromService;
import com.goodsmanage.loh01.pojo.Result;

import java.util.List;

// 导入Spring Web REST控制器注解
@Slf4j
@RestController
// Spring注解：标识这是一个REST控制器
public class goodsFromController {
    // 商品来源控制器类，公共访问修饰符
    @Autowired
    //这行代码为了能够私有访问到Service
    private goodsFromService goodsFromService;

    @GetMapping("/goodsfrom")
    public Result list() {
        List<goodsfrom> goodsFromList=goodsFromService.list();
        log.info("获取商品来源信息成功");
        return Result.success(goodsFromList);
    }
    @DeleteMapping("/goodsfrom/{id}")
    public Result delete(@PathVariable Integer id) {
        goodsFromService.delete(id);
        log.info("删除商品品牌{}成功",id);
        return Result.success();
    }
    @PostMapping("/createGoodsFrom")
    public Result createGoodsFrom(@RequestBody goodsfrom goodsfrom){
        goodsFromService.createGoodsFrom(goodsfrom);
        log.info("新增商品来源信息成功");
        return Result.success(goodsfrom);
    }
    @PutMapping("/goodsFromMod/{id}")
    public Result updateGoodsFrom(@PathVariable Integer id, @RequestBody goodsfrom goodsfrom){
        goodsFromService.updateGoodsFrom(id,goodsfrom);
        log.info("修改商品来源信息成功");
        return Result.success(goodsfrom);
    }
    @GetMapping("/goodsFromList")
    public Result goodsFromList(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "10") Integer pageSize){
        Integer total = goodsFromService.total();
        List<goodsfrom> goodsFromList = goodsFromService.rows(page-1,pageSize);
        log.info("获取商品来源信息列表成功！商品信息来源字段总个数为："+total);
        return Result.success(goodsFromList);
    }

}