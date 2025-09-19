package com.goodsmanage.loh01.service;
// 包声明，服务层包

import com.baomidou.mybatisplus.extension.service.IService;
import com.goodsmanage.loh01.entity.Goodsfrom;
import com.goodsmanage.loh01.entity.User;

import java.util.List;
// 导入Spring Service注解


// Spring注解：标识这是一个服务类
public interface goodsFromService  extends IService<Goodsfrom> {


    Integer total();

    List<User> row(Integer page, Integer pageSize);
}