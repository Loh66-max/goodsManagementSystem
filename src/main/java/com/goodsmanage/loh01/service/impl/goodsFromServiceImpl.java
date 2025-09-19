package com.goodsmanage.loh01.service.impl;
// 包声明，服务实现类包

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.mapper.goodsFromMapper;
import com.goodsmanage.loh01.entity.Goodsfrom;
import com.goodsmanage.loh01.service.goodsFromService;
// 导入商品来源服务接口
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// 导入Spring Service注解

@Service
// Spring注解：标识这是一个服务实现类
public class goodsFromServiceImpl extends ServiceImpl<goodsFromMapper, Goodsfrom> implements goodsFromService {
    // 商品来源服务实现类，实现商品来源服务接口
    // 目前为空实现类，可以后续添加商品来源相关的业务逻辑
    @Autowired
    private goodsFromMapper goodsFromMapper;

    @Override
    public Integer total() {
        return goodsFromMapper.total();
    }

    @Override
    public List<User> row(Integer page, Integer pageSize) {
        return goodsFromMapper.rows(page,pageSize);
    }
}