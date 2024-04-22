package com.example.healthassistant.water;

import com.example.healthassistant.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WaterRepository extends JpaRepository<Water, Long> {
    Iterable<Water> findAllByUser(User user);
}
