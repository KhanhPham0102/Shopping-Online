package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.app.repository")
@EntityScan("com.app.model")
@EnableCaching
public class KhanhApplication {

    public static void main(String[] args) {
        SpringApplication.run(KhanhApplication.class, args);
    }

}
