package com.example.healthassistant.jwt.model.repository;

import com.example.healthassistant.jwt.model.entity.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
