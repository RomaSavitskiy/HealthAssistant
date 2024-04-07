package com.example.healthassistant.repository;

import com.example.healthassistant.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
