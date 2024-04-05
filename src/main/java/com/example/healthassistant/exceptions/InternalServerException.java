package com.example.healthassistant.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InternalServerException extends RuntimeException {
    private String message;
    private Long status;
}
