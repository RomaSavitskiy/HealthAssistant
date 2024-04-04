package com.example.healthassistant.model.response;

public record UserResponseTo(
        Long id,
        String email,
        String firstname,
        String lastname) {
}

