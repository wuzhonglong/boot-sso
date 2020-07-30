package com.wzl.bootsso;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wzl.bootsso.mapper")
public class BootSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootSsoApplication.class, args);
    }

}
