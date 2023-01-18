package com.fybgame.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
/**
 * @Author:fyb
 * @Date: 2021/2/8 17:38
 * @Version:1.0
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableTransactionManagement
public class WebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) { return application.sources(WebApplication.class); }
    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
