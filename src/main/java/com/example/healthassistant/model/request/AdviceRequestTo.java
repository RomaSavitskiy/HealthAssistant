package com.example.healthassistant.model.request;

import com.example.healthassistant.model.enums.AdviceCategory;
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
