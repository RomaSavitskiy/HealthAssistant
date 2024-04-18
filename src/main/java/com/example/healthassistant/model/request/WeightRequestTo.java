package com.example.healthassistant.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record WeightRequestTo (
        @NotBlank
        @Schema(example = "100")
        Long weight,

        LocalDate date
)
{
}
