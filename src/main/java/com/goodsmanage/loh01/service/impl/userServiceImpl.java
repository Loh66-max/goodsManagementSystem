package com.goodsmanage.loh01.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodsmanage.loh01.mapper.userMapper;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class userServiceImpl extends ServiceImpl<userMapper, User> implements userService {
    @Autowired
    private userMapper userMapper;

    @Override
    public Integer total() {
        return userMapper.total();
    }

    @Override
    public List<User> row(Integer page, Integer pageSize) {
        return userMapper.rows(page,pageSize);
    }
}
