package com.citrine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.citrine.controllers",
    "com.citrine.exceptions",
    "com.citrine.services"})

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
