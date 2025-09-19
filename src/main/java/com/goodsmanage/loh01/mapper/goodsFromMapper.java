package com.goodsmanage.loh01.mapper;
// 包声明，数据访问层包

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodsmanage.loh01.entity.Goodsfrom;
import com.goodsmanage.loh01.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
// 导入MyBatis Mapper注解

@Mapper
// MyBatis注解：标识这是一个Mapper接口
public interface goodsFromMapper extends BaseMapper<Goodsfrom> {

    @Select("select count(*) from  user")
    Integer total();

    @Select("select * from user limit #{page},#{pageSize}")
    List<User> rows(@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}