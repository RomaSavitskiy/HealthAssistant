package com.example.healthassistant.jwt.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private Long tokenId;

    @Column(name="confirmation_token")
    private String ConfirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
}
