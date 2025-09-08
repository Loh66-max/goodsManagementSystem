package com.goodsmanage.loh01.service;
// 包声明，服务层包

import com.goodsmanage.loh01.pojo.goods;
// 导入商品实体类
import com.goodsmanage.loh01.pojo.page;
import org.springframework.stereotype.Service;
// 导入Spring Service注解

import java.util.List;
// 导入List集合类

@Service
// Spring注解：标识这是一个服务类，会被Spring容器管理
public interface goodsService {
    // 商品服务接口，公共访问修饰符


    //查询全部部门数据
    // 注释：查询全部商品数据（注释中的"部门"应该是"商品"的笔误）
    List<goods> list();

    void createGoods(goods goods);

    void deleteGoods(int id);

    void goodsMod(int id, goods goods);

    Integer total();

    List<goods> rows(Integer page, Integer pageSize);
}