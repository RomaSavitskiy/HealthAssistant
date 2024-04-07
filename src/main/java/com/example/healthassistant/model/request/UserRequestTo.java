package com.example.healthassistant.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestTo(
    Long id,
    @NotBlank
    @Size(min = 2, max = 64)
    String username,
    @NotBlank
    @Size(min = 4, max = 128)
    String password,
    @NotBlank
    @Size(min = 2, max = 64)
    String firstname,
    @NotBlank
    @Size(min = 2, max = 64)
    String lastname
) {
}

