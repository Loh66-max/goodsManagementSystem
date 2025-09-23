package com.goodsmanage.loh01.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data

public class Goods {
    private int id;
    @TableField("goodsName")
    private String goodsName;
    @TableField("goodsNumber")
    private String goodsNumber;
    @TableField("goodsFrom")
    private String goodsFrom;
    @TableField("goodsPrice")
    private String goodsPrice;
    @TableField("create_time")
    private String createTime;
}
