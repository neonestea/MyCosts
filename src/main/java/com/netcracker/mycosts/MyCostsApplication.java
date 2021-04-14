package com.netcracker.mycosts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyCostsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyCostsApplication.class, args);
    }

}
