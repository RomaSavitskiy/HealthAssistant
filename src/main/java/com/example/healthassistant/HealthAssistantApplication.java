package com.example.healthassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class HealthAssistantApplication {
    @Profile({"dev"})

    public static void main(String[] args) {
        SpringApplication.run(HealthAssistantApplication.class, args);
    }

}
