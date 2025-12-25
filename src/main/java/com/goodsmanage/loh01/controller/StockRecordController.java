package com.goodsmanage.loh01.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.goodsmanage.loh01.entity.Result;
import com.goodsmanage.loh01.entity.StockRecord;
import com.goodsmanage.loh01.service.StockRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class StockRecordController {

    @Autowired
    private StockRecordService stockRecordService;

    /**
     * 分页查询（对应前端 /pageStockRecord）
     */
    @GetMapping("/pageStockRecord")
    public Result pageStockRecord(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<StockRecord> pageInfo = new Page<>(page, pageSize);

        stockRecordService.page(pageInfo);

        return Result.success(pageInfo);
    }

    /**
     * 条件查询（对应前端 /queryStockRecord）
     */
    @PostMapping("/queryStockRecord")
    public Result queryStockRecord(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestBody StockRecord record) {

        Page<StockRecord> pageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<StockRecord> wrapper = new LambdaQueryWrapper<>();

        if (record.getGoodsName() != null && !record.getGoodsName().isEmpty()) {
            wrapper.like(StockRecord::getGoodsName, record.getGoodsName());
        }

        if (record.getType() != null) {
            wrapper.eq(StockRecord::getType, record.getType());
        }

        if (record.getOperator() != null && !record.getOperator().isEmpty()) {
            wrapper.like(StockRecord::getOperator, record.getOperator());
        }

        wrapper.orderByDesc(StockRecord::getCreateTime);

        stockRecordService.page(pageInfo, wrapper);

        return Result.success(pageInfo);
    }

    /**
     * 自动写入出入库记录（由出入库操作调用）
     */
    @PostMapping("/saveStockRecord")
    public Result saveStockRecord(@RequestBody StockRecord stockRecord) {
        boolean ok = stockRecordService.save(stockRecord);
        if (ok) {
            return Result.success("记录已写入");
        }
        return Result.error("写入失败");
    }
}
