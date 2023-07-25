package com.example.tourarmeniaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@EnableAsync
@SpringBootApplication
@ComponentScan(basePackages = {"com.example.tourarmeniaweb", "com.example.tourarmeniacommon"})
@EntityScan("com.example.tourarmeniacommon.entity")
@EnableJpaRepositories(basePackages = "com.example.tourarmeniacommon.repository")
public class TourArmeniaWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourArmeniaWebApplication.class, args);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

}
