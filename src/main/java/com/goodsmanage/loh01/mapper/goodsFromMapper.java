package com.goodsmanage.loh01.mapper;
// 包声明，数据访问层包

import com.goodsmanage.loh01.pojo.goodsfrom;
import org.apache.ibatis.annotations.*;

import java.util.List;
// 导入MyBatis Mapper注解

@Mapper
// MyBatis注解：标识这是一个Mapper接口
public interface goodsFromMapper {
    // 商品来源数据访问接口，公共访问修饰符
    // 目前为空接口，可以后续添加商品来源相关的数据访问方法
    @Select("select * from goodsfrom")

    List<goodsfrom> list();


    @Delete("delete from goodsfrom where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into goodsfrom(id,name)values (#{id},#{name})")
    void createGoodsFrom(goodsfrom goodsfrom);

    @Update("update goodsfrom  set name=#{goodsfrom.name} where id=#{id}")
    void updateGoodsFrom(@Param("id") Integer id,@Param("goodsfrom") goodsfrom goodsfrom);

    @Select("select count(*)from goodsfrom")
    Integer total();

    @Select("select * from goodsfrom limit #{page},#{pageSize}")
    List<goodsfrom> rows(@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}