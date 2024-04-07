package com.example.healthassistant;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/")})
public class HealthAssistantApplication {
    @Profile({"dev"})
    public static void main(String[] args) {
        SpringApplication.run(HealthAssistantApplication.class, args);
    }

}
