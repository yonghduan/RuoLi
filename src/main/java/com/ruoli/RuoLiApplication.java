package com.ruoli;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ruoli.mapper")
public class RuoLiApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoLiApplication.class,args);
        System.out.println("若离启动成功");
    }
}
