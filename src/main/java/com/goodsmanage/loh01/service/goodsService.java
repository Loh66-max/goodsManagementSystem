package com.goodsmanage.loh01.service;
// 包声明，服务层包

import com.baomidou.mybatisplus.extension.service.IService;
import com.goodsmanage.loh01.entity.Goods;
import com.goodsmanage.loh01.entity.User;

import java.util.List;
// 导入商品实体类
// 导入Spring Service注解

// 导入List集合类


// Spring注解：标识这是一个服务类，会被Spring容器管理
public interface goodsService extends IService<Goods> {

    Integer total();

    List<User> row(Integer page, Integer pageSize);
}