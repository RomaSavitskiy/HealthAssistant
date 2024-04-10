package com.example.healthassistant.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class InternalServerException extends RuntimeException {
    private Long status;
    private String message;
}
