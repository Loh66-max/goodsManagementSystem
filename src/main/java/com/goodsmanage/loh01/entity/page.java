package com.goodsmanage.loh01.entity;

import lombok.Data;
import java.util.List;

@Data
public class page {
    private static int pageNo = 1;
    private static int pageSize = 10 ;

    private Integer total;
    private List rows;
}
