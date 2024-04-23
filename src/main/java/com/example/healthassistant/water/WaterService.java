package com.example.healthassistant.water;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public interface WaterService {
    WaterResponseTo save(@Valid WaterRequestTo waterRequestTo, String token);

    Iterable<WaterResponseTo> findAllForUser(String token);

    WaterResponseTo update(@Valid WaterRequestTo waterRequestTo, String token);

    void deleteById(@Min(0) Long id);
}
