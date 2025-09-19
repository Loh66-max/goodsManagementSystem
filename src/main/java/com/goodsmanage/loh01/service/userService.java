package com.goodsmanage.loh01.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.goodsmanage.loh01.entity.User;

import java.util.List;


public interface userService extends IService<User> {


    Integer total();

    List<User> row(Integer page, Integer pageSize);
}
