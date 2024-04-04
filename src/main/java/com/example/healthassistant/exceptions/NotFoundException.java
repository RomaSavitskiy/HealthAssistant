package com.example.healthassistant.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private String message;
    private Long status;
}
