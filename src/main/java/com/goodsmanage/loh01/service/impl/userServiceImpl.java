package com.goodsmanage.loh01.service.impl;

import com.goodsmanage.loh01.mapper.userMapper;
import com.goodsmanage.loh01.pojo.user;
import com.goodsmanage.loh01.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class userServiceImpl implements userService {
    @Autowired
    private userMapper userMapper;
    @Override
    public List<user> list(String num) {
        return userMapper.list(num);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public void create(user user) {
        userMapper.create(user);
    }

    @Override
    public void mod(Integer id, user user) {
        userMapper.mod(id,user);
    }

    @Override
    public Integer total() {
        return userMapper.total();

    }

    @Override
    public List<user> row(Integer page, Integer pageSize,String num) {
        return userMapper.rows(page,pageSize,num);
    }
}
