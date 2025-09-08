package com.goodsmanage.loh01.service.impl;
// 包声明，服务实现类包

import com.goodsmanage.loh01.mapper.goodsFromMapper;
import com.goodsmanage.loh01.pojo.goodsfrom;
import com.goodsmanage.loh01.service.goodsFromService;
// 导入商品来源服务接口
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
// 导入Spring Service注解

@Service
// Spring注解：标识这是一个服务实现类
public class goodsFromServiceImpl implements goodsFromService {
    // 商品来源服务实现类，实现商品来源服务接口
    // 目前为空实现类，可以后续添加商品来源相关的业务逻辑
    @Autowired
    private goodsFromMapper goodsFromMapper;

    @Override
    public List<goodsfrom> list(){
        return goodsFromMapper.list();
    }

    @Override
    public void delete(Integer id) {
        goodsFromMapper.deleteById(id);
    }

    @Override
    public void createGoodsFrom(goodsfrom goodsfrom) {
        goodsFromMapper.createGoodsFrom(goodsfrom);
    }

    @Override
    public void updateGoodsFrom(Integer id, goodsfrom goodsfrom) {
        goodsFromMapper.updateGoodsFrom(id,goodsfrom);
    }

    @Override
    public Integer total() {
        return goodsFromMapper.total();
    }

    @Override
    public List<goodsfrom> rows(Integer page, Integer pageSize) {
        return goodsFromMapper.rows(page,pageSize);
    }
}