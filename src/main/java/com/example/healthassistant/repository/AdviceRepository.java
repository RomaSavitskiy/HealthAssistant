package com.example.healthassistant.repository;

import com.example.healthassistant.model.entity.Advice;
import com.example.healthassistant.model.enums.AdviceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {
    Iterable<Advice> findAllByCategory(AdviceCategory adviceCategory);
}
