package com.goodsmanage.loh01.mapper;

import com.goodsmanage.loh01.pojo.user;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface userMapper {
    @Select("SELECT * from user")
    List<user> list();

    @Delete("delete from user where id=#{id}")
    void delete(Integer id);

    @Insert("insert into user(id,num,password,role,value) values (#{id},#{num},#{password},#{role},#{value})")
    void create(user user);

    @Update("update user set num=#{user.num},password=#{user.password},role=#{user.role},value=#{user.value} where id=#{id}")
    void mod(@Param("id") Integer id, @Param("user") user user);

    @Select("select count(*) from  user")
    Integer total();

    @Select("select * from user limit #{page},#{pageSize}")
    List<user> rows(@Param("page") Integer page,@Param("pageSize") Integer pageSize);
}
