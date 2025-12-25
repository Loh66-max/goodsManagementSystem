package com.goodsmanage.loh01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goodsmanage.loh01.entity.Goods;
import com.goodsmanage.loh01.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService extends IService<Order> {
    Integer total();

    List<Order> row(Integer page, Integer pageSize);
}
