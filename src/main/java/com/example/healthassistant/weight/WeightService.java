package com.example.healthassistant.weight;

public interface WeightService {
    WeightResponseTo save(WeightRequestTo weightRequestTo, String token);

    Weight findById(Long id);

    Iterable<WeightResponseTo> findAllForUser(String token);

    WeightResponseTo update(WeightRequestTo weight, String token)
            throws IllegalAccessException;

    void deleteById(Long id);
}
