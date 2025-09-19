package com.goodsmanage.loh01.entity;

import lombok.Data;

@Data

public class Goods {
    private int id;
    private String goodsName;
    private String goodsNumber;
    private String goodsFrom;
    private String goodsPrice;
    private String createTime;
}
