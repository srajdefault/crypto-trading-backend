package com.sumit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TradingProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradingProjectApplication.class, args);
    }

}
