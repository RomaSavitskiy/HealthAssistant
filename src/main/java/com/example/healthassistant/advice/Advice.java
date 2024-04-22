package com.example.healthassistant.advice;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Advice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 600)
    private String text;

    @Enumerated(EnumType.STRING)
    private AdviceCategory category;
}
