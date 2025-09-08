package com.goodsmanage.loh01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.goodsmanage.loh01.mapper")
public class Loh01Application {

    public static void main(String[] args) {
        SpringApplication.run(Loh01Application.class, args);
    }

}
