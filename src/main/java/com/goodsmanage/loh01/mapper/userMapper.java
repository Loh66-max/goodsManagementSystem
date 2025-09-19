package com.goodsmanage.loh01.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodsmanage.loh01.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface userMapper extends BaseMapper<User> {

    @Select("select count(*) from  user")
    Integer total();

    @Select("select * from user limit #{page},#{pageSize}")
    List<User> rows(@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}
