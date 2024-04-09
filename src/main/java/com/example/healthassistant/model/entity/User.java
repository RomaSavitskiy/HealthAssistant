package com.example.healthassistant.model.entity;

import com.example.healthassistant.jwt.model.entity.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@ToString
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    private String username;

    @JsonIgnore
    private String password;

    private String firstname;

    private String lastname;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles = new HashSet<>();
}
