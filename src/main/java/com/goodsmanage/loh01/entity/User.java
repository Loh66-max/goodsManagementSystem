package com.goodsmanage.loh01.entity;

import lombok.Data;

@Data
public class User {
    private int id;
    private String num;
    private String password;
    private int role;
    private String value;

}
