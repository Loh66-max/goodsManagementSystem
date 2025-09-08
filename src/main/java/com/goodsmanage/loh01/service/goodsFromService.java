package com.goodsmanage.loh01.service;
// 包声明，服务层包

import com.goodsmanage.loh01.pojo.goodsfrom;
import com.goodsmanage.loh01.pojo.page;
import org.springframework.stereotype.Service;

import java.util.List;
// 导入Spring Service注解

@Service
// Spring注解：标识这是一个服务类
public interface goodsFromService {

    List<goodsfrom> list();


    void delete(Integer id);

    void createGoodsFrom(goodsfrom goodsfrom);

    void updateGoodsFrom(Integer id, goodsfrom goodsfrom);

    Integer total();

    List<goodsfrom> rows(Integer page, Integer pageSize);
}