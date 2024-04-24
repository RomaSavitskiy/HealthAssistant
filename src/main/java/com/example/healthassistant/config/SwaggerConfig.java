package com.example.healthassistant.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "HealthAssistant API",  // Название вашего API
                version = "1.0",        // Версия вашего API
                description = "API для управления ресурсами и взаимодействия с клиентами приложения HealthAssistant.",  // Описание API
                termsOfService = "https://example.com/terms",  // Ссылка на условия использования
                contact = @Contact(  // Информация о контактах
                        name = "Github",  // Имя контактного лица
                        email = "roma.savitskiiy@gmail.com",
                        url = "https://github.com/RomaSavitskiy/HealthAssistant/tree/master"// Электронная почта для связи
                )
        )
)
public class SwaggerConfig {

    private io.swagger.v3.oas.models.security.SecurityScheme createAPIKeyScheme() {
        return new io.swagger.v3.oas.models.security.SecurityScheme().type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer");
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()));
    }
}
