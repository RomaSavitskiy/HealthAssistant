package com.example.healthassistant.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class IncorrectCodeException extends RuntimeException {
    private Long status;
    private String message;
}
