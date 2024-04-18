package com.example.healthassistant.repository;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Weight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    Iterable<Weight> findAllByUser(User user);
}
