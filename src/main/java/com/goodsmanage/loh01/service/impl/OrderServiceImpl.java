package com.goodsmanage.loh01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodsmanage.loh01.entity.Goods;
import com.goodsmanage.loh01.entity.Order;
import com.goodsmanage.loh01.mapper.orderMapper;
import com.goodsmanage.loh01.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl extends ServiceImpl<orderMapper, Order> implements OrderService {
    @Autowired
    private orderMapper orderMapper;
    @Override
    public Integer total() {
        return orderMapper.total();
    }

    @Override
    public List<Order> row(Integer page, Integer pageSize) {
        return orderMapper.rows(page, pageSize);
    }
}
