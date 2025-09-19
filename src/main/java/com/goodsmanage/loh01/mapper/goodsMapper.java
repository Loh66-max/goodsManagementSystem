package com.goodsmanage.loh01.mapper;
// 包声明，数据访问层包

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodsmanage.loh01.entity.User;
import com.goodsmanage.loh01.entity.Goods;
// 导入商品实体类
import org.apache.ibatis.annotations.*;
// 导入MyBatis Mapper注解
// 导入MyBatis Select注解

import java.util.List;
// 导入List集合类

@Mapper
// MyBatis注解：标识这是一个Mapper接口，Spring会自动创建实现类
public interface goodsMapper extends BaseMapper<Goods> {
    @Select("select count(*) from  user")
    Integer total();

    @Select("select * from user limit #{page},#{pageSize}")
    List<User> rows(@Param("page") Integer page, @Param("pageSize") Integer pageSize);
}