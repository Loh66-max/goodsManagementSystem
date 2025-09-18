package com.goodsmanage.loh01.service;

import com.goodsmanage.loh01.pojo.user;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface userService {

    List<user> list(String num);

    void delete(Integer id);

    void create(user user);

    void mod(Integer id, user user);

    Integer total();

    List<user> row(Integer page, Integer pageSize,String num);
}
