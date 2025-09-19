package com.goodsmanage.loh01.service.impl;
// 包声明，服务实现类包

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.mapper.goodsMapper;
// 导入商品数据访问接口
import com.goodsmanage.loh01.entity.Goods;
// 导入商品实体类
import com.goodsmanage.loh01.service.goodsService;
// 导入商品服务接口
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring自动装配注解
import org.springframework.stereotype.Service;

import java.util.List;
// 导入Spring Service注解

// 导入List集合类
@Service
// Spring注解：标识这是一个服务实现类
public class goodsServiceImpl extends ServiceImpl<goodsMapper, Goods> implements goodsService {
    // 商品服务实现类，实现商品服务接口

    @Autowired
    // Spring注解：自动装配商品数据访问对象
    private goodsMapper GoodsMapper;

    @Override
    public Integer total() {
        return GoodsMapper.total();
    }

    @Override
    public List<User> row(Integer page, Integer pageSize) {
        return GoodsMapper.rows(page, pageSize);
    }
    // 商品数据访问对象，私有访问修饰符


}