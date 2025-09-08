package com.goodsmanage.loh01.service.impl;
// 包声明，服务实现类包

import com.goodsmanage.loh01.mapper.goodsMapper;
// 导入商品数据访问接口
import com.goodsmanage.loh01.pojo.goods;
// 导入商品实体类
import com.goodsmanage.loh01.pojo.page;
import com.goodsmanage.loh01.service.goodsService;
// 导入商品服务接口
import org.springframework.beans.factory.annotation.Autowired;
// 导入Spring自动装配注解
import org.springframework.stereotype.Service;
// 导入Spring Service注解

import java.util.List;
// 导入List集合类
@Service
// Spring注解：标识这是一个服务实现类
public class goodsServiceImpl implements goodsService {
    // 商品服务实现类，实现商品服务接口

    @Autowired
    // Spring注解：自动装配商品数据访问对象
    private goodsMapper GoodsMapper;
    // 商品数据访问对象，私有访问修饰符

    @Override
    // 重写接口方法
    public List<goods> list() {
        // 查询所有商品的实现方法
        return GoodsMapper.list();
        // 调用数据访问层的方法查询所有商品并返回
    }

    @Override
    public void createGoods(goods goods) {
       GoodsMapper.createGoods(goods);
    }

    @Override
    public void deleteGoods(int id) {
       GoodsMapper.deleteGoods(id);
    }


    @Override
    public void goodsMod(int id,goods goods) {
        GoodsMapper.goodsMod(id,goods);
    }

    @Override
    public Integer total() {
        return GoodsMapper.total();
    }

    @Override
    public List<goods> rows(Integer page, Integer pageSize) {
        return GoodsMapper.rows(page,pageSize);
    }


}