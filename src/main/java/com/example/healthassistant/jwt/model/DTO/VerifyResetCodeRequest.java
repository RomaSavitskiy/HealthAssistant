package com.example.healthassistant.jwt.model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyResetCodeRequest {
    @Schema(example = "admin")
    private String username;
    @Schema(example = "admin")
    private String code;
}
