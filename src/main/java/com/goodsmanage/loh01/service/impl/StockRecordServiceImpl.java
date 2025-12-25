package com.goodsmanage.loh01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodsmanage.loh01.entity.StockRecord;
import com.goodsmanage.loh01.mapper.StockRecordMapper;
import com.goodsmanage.loh01.service.StockRecordService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StockRecordServiceImpl extends ServiceImpl<StockRecordMapper, StockRecord> implements StockRecordService {
    @Override
    public Map<String, Object> list(Map<String, Object> params) {
        return Map.of();
    }
}
