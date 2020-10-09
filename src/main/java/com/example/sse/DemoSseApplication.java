package com.example.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoSseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoSseApplication.class, args);
    }

}
