package com.goodsmanage.loh01.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class StockRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Integer goodsId;       // 货品ID
    private String goodsName;      // 货品名称（冗余）
    private Integer type;          // 1=入库，2=出库
    private Integer quantity;      // 变动数量
    private Integer beforeStock;   // 操作前库存
    private Integer afterStock;    // 操作后库存
    private String operator;       // 操作人
    private Date createTime;       // 操作时间
}