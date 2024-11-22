package com.homeseek.finalproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.homeseek")
@MapperScan("com.homeseek.map.mapper")
@MapperScan("com.homeseek.dealdata.mapper")
@MapperScan("com.homeseek.sale.mapper")
@MapperScan("com.homeseek.user.mapper")
public class FinalProjectApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }
}
