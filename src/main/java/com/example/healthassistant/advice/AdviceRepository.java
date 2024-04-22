package com.example.healthassistant.advice;

import com.example.healthassistant.advice.Advice;
import com.example.healthassistant.advice.AdviceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {
    Iterable<Advice> findAllByCategory(AdviceCategory adviceCategory);
}
