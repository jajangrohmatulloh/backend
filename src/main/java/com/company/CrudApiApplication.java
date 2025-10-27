package com.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@ComponentScan(basePackages = {"com.company", "com.company.controller", "com.company.security"})
public class CrudApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudApiApplication.class, args);
    }

}