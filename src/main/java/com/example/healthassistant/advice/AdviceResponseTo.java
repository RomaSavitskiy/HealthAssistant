package com.example.healthassistant.advice;

import com.example.healthassistant.advice.AdviceCategory;

public record AdviceResponseTo(
        String title,
        String text,
        AdviceCategory category
) {
}
