package com.goodsmanage.loh01.controller;
// 包声明，控制器层包

// 导入统一响应结果类
// 导入商品实体类
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.common.UserContext;
import com.goodsmanage.loh01.entity.Goods;
import com.goodsmanage.loh01.entity.Result;
import com.goodsmanage.loh01.entity.StockRecord;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.service.StockRecordService;
import com.goodsmanage.loh01.service.goodsService;
// 导入商品服务接口
import lombok.extern.slf4j.Slf4j;
// 导入Lombok日志注解
// 导入SLF4J日志工厂类
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring自动装配注解
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    @Autowired
    private StockRecordService stockRecordService;
    @GetMapping("/listGoods")
    public Result list(){
        List<Goods> list = goodsService.list();
        log.info("success");
        return Result.success(list);
    }
    @PostMapping("/saveGoods")
    public boolean save(@RequestBody Goods goods){
        log.info("save user");
        return goodsService.save(goods);
    }
    @PostMapping("/modGoods")
    public boolean mod(@RequestBody Goods goods,
                   @RequestParam(required = false) String operator){
        log.info("mod goods id: {}", goods.getId());

        if (goods.getId()==0) {
            log.warn("修改请求缺少 id");
            return false;
        }

        // 1. 获取修改前记录
        Goods old = goodsService.getById(goods.getId());
        if (old == null) {
            log.warn("未找到 id={} 的货品", goods.getId());
            return false;
        }

        Integer before = old.getGoodsNumber() == null ? 0 : Integer.valueOf(old.getGoodsNumber());
        Integer after = goods.getGoodsNumber() == null ? before : Integer.valueOf(goods.getGoodsNumber());

        // 2. 更新商品（这里保留原有 updateById 的行为）
        boolean updateResult = goodsService.updateById(goods);

        // 3. 如果库存发生变更，写入出入库记录

        if (updateResult && (after.intValue() != before.intValue())) {

    try {
        StockRecord sr = new StockRecord();
        sr.setGoodsId(goods.getId());
        sr.setGoodsName(StringUtils.isNotBlank(goods.getGoodsName()) ? goods.getGoodsName() : old.getGoodsName());
        if (after > before) {
            sr.setType(1); // 入库
            sr.setQuantity(after - before);
        } else {
            sr.setType(2); // 出库
            sr.setQuantity(before - after);
        }
        sr.setBeforeStock(before);
        sr.setAfterStock(after);

        // ★ 优先使用前端传过来的 operator；否则从 JWT 中获取当前登录用户；最后使用 system
        String finalOperator;
        if (StringUtils.isNotBlank(operator)) {
            finalOperator = operator;
        } else {
            User currentUser = UserContext.getCurrentUser();
            if (currentUser != null && StringUtils.isNotBlank(currentUser.getNum())) {
                finalOperator = currentUser.getNum();
            } else {
                finalOperator = "system";
            }
        }
        sr.setOperator(finalOperator);
        sr.setCreateTime(new java.util.Date());

        stockRecordService.save(sr);
        log.info(finalOperator);
        log.info("出入库记录写入成功, goodsId={}, type={}, qty={}, operator={}",
                sr.getGoodsId(), sr.getType(), sr.getQuantity(), finalOperator);
    } catch (Exception e) {
        log.error("写入出入库记录失败，事务将回滚", e);
        throw e;
    }
}

        return updateResult;
    }

    @PostMapping("/saveOrUpdateGoods")
    public boolean saveOrUpdate(@RequestBody Goods goods){
        log.info("saveOrUpdate user");
        return goodsService.saveOrUpdate(goods);
    }
    @GetMapping("/deleteGoods")
    public boolean delete(Integer id){
        log.info("delete user");
        return goodsService.removeById(id);
    }
    @PostMapping("/queryGoods")
    public Page<Goods> query(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody Goods goods) {
        // 创建分页对象
        Page<Goods> pageInfo = new Page<>(page, pageSize);
        // 创建查询条件
        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(goods.getGoodsName()), Goods::getGoodsName, goods.getGoodsName());
        lambdaQueryWrapper.like(StringUtils.isNotBlank(goods.getGoodsFrom()), Goods::getGoodsFrom, goods.getGoodsFrom());

        // 创建时间范围查询
        if (StringUtils.isNotBlank(goods.getCreateTime())) {
            // 假设前端传递的是时间范围字符串，格式为 "startTime,endTime"
            String[] timeRange = goods.getCreateTime().split(",");
            if (timeRange.length == 2) {
                lambdaQueryWrapper.between(Goods::getCreateTime, timeRange[0], timeRange[1]);
            } else {
                // 如果只有一个时间，则进行模糊查询
                lambdaQueryWrapper.like(Goods::getCreateTime, goods.getCreateTime());
            }
        }
        return goodsService.page(pageInfo, lambdaQueryWrapper);
    }
    @GetMapping("/pageGoods")
    public Result getUserList(@RequestParam(defaultValue = "1") Integer page,
                              @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer total = goodsService.total();
        List<Goods> row = goodsService.row((page - 1) * pageSize, pageSize);
        Map<String, Object> data = new HashMap<>();
        data.put("list", row);
        data.put("total", total);
        return Result.success(data);
    }
    @GetMapping("/lowStock")
    public Result getLowStockGoods() {
        try {
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.lt(Goods::getGoodsNumber, 10); // 库存 < 10
            List<Goods> lowStockGoods = goodsService.list(wrapper);
            return Result.success(lowStockGoods);
        } catch (Exception e) {
            return Result.error("查询库存预警失败");
        }
    }
    @GetMapping("/inventoryWarning")
    public Result inventoryWarning() {
        try {
            LambdaQueryWrapper<Goods> wrapper = new LambdaQueryWrapper<>();
            wrapper.lt(Goods::getGoodsNumber, 10); // 库存 < 10

            List<Goods> warningList = goodsService.list(wrapper);

            return Result.success(warningList);
        } catch (Exception e) {
            log.error("获取库存预警失败", e);
            return Result.error("获取库存预警失败");
        }
    }

}