package com.example.healthassistant.weight;

import com.example.healthassistant.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepository extends JpaRepository<Weight, Long> {
    Iterable<Weight> findAllByUser(User user);
}
