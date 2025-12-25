package com.goodsmanage.loh01.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodsmanage.loh01.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
@Mapper
public interface orderMapper extends BaseMapper<Order> {
    @Select("select count(*) from  `order`")
    Integer total();

    @Select("select * from `order` limit #{page},#{pageSize}")
    List<Order> rows(@Param("page") Integer page, @Param("pageSize") Integer pageSize);
;
}
