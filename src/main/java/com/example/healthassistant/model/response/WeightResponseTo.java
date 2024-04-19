package com.example.healthassistant.model.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WeightResponseTo (
        Long id,
        Long weight,

        String localDate
)
{
}
