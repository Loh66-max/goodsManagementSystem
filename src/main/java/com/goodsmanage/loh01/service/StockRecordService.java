package com.goodsmanage.loh01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goodsmanage.loh01.entity.StockRecord;

import java.util.Map;

public interface StockRecordService extends IService<StockRecord> {
    Map<String, Object> list(Map<String, Object> params);
}
