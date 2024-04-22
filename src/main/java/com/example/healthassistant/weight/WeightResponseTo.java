package com.example.healthassistant.weight;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record WeightResponseTo (
        Long weight,

        String dateTime
)
{
}
