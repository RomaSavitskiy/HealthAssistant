package com.example.healthassistant.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record WaterRequestTo (
        @NotBlank
        @Schema(example = "200")
        Long volume,

        @NotBlank
        String dateTime
) {
}
