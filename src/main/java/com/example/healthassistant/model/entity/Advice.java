package com.example.healthassistant.model.entity;

import com.example.healthassistant.model.enums.AdviceCategory;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    @Enumerated(EnumType.STRING)
    private AdviceCategory category;
}
