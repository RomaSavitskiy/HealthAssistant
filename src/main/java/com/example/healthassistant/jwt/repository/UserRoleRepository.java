package com.example.healthassistant.jwt.repository;

import com.example.healthassistant.jwt.model.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
}
