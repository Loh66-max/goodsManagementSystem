package com.goodsmanage.loh01.mapper;
// 包声明，数据访问层包

import com.goodsmanage.loh01.pojo.goods;
// 导入商品实体类
import com.goodsmanage.loh01.pojo.page;
import org.apache.ibatis.annotations.*;
// 导入MyBatis Mapper注解
// 导入MyBatis Select注解

import java.util.List;
// 导入List集合类

@Mapper
// MyBatis注解：标识这是一个Mapper接口，Spring会自动创建实现类
public interface goodsMapper {


    void deleteGoods(int id);

    // 商品数据访问接口，公共访问修饰符
    @Select("select * from goods")
    // MyBatis注解：定义SQL查询语句，查询goods表的所有数据
    List<goods> list();

    @Insert("INSERT INTO goods(id,goodsName,goodsNumber,goodsFrom,goodsPrice) values (#{id},#{goodsName},#{goodsNumber},#{goodsFrom},#{goodsPrice})" )
    void createGoods(goods goods);


    @Update("update goods set goodsName=#{goods.goodsName},goodsNumber=#{goods.goodsNumber},goodsFrom=#{goods.goodsFrom},goodsPrice=#{goods.goodsPrice} where id=#{goods.id}")
    void goodsMod(@Param("id") int id,@Param("goods") goods goods);
    // 查询所有商品的方法，返回商品列表

    @Select("select count(*) from goods")
    Integer total();

    @Select("select * from goods limit #{page},#{pageSize}")
    List<goods> rows(@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}