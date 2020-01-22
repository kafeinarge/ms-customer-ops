package com.turkcell.poc.customerupdater;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.turkcell.poc.customerupdater.repository")
@SpringBootApplication
@EnableCaching
public class CustomerUpdaterApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerUpdaterApplication.class, args);
    }

}
