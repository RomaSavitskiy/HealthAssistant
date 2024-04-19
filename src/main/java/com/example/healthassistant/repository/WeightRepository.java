package com.example.healthassistant.repository;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    Iterable<Weight> findAllByUser(User user);
}
