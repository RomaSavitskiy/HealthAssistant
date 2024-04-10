package com.example.healthassistant.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
@AllArgsConstructor
public class ErrorMessage {
    private Long errorCode;
    private String errorMessage;
}
