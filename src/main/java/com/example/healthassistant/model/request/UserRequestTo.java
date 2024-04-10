package com.example.healthassistant.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Сущность пользователя")
public record UserRequestTo(
    @NotBlank
    @Size(min = 2, max = 64)
    @Schema(example = "admin")
    String username,
    @NotBlank
    @Size(min = 4, max = 128)
    @Schema(example = "admin")
    String password,
    @NotBlank
    @Size(min = 2, max = 64)
    @Schema(example = "admin")
    String firstname,
    @NotBlank
    @Size(min = 2, max = 64)
    @Schema(example = "admin")
    String lastname
) {
}

