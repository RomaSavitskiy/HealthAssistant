package com.example.healthassistant.advice;

import com.example.healthassistant.advice.AdviceCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AdviceRequestTo(
              @NotBlank
              @Size(min = 3, max = 24)
              String title,

              @NotBlank
              @Size(min = 12, max = 128)
              String text,
              @NotBlank
              AdviceCategory category
){
}
