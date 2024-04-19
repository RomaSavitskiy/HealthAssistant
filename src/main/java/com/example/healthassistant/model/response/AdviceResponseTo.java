package com.example.healthassistant.model.response;

import com.example.healthassistant.model.enums.AdviceCategory;

public record AdviceResponseTo(
        Long id,
        String title,
        String text,
        AdviceCategory category
) {
}
