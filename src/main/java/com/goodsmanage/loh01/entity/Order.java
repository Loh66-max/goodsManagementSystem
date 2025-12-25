package com.goodsmanage.loh01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
@TableName("`order`")
public class Order{
    private int id;
    @TableField("order_no")
    private String orderNo;
    @TableField("customer_name")
    private String customerName;
    @TableField("order_amount")
    private BigDecimal orderAmount;
    @TableField("order_remark")
    private String orderRemark;
    @TableField("create_time")
    private String createTime;

}
