package com.awei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.awei.dao")
public class EmpSystemApplication{

    public static void main(String[] args) {
        SpringApplication.run(EmpSystemApplication.class, args);
    }


}
