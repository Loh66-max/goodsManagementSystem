package com.goodsmanage.loh01.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class goods {
    private int id;
    private String goodsName;
    private String goodsNumber;
    private String goodsFrom;
    private String goodsPrice;
    private String createTime;
}
