package com.example.healthassistant.repository;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Water;
import com.example.healthassistant.model.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterRepository extends JpaRepository<Water, Long> {
    Iterable<Water> findAllByUser(User user);
}
