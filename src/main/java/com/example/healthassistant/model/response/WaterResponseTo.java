package com.example.healthassistant.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record WaterResponseTo (
        @NotBlank
        @Schema(example = "200")
        Long volume,

        @NotBlank
        String dateTime
) {
}
