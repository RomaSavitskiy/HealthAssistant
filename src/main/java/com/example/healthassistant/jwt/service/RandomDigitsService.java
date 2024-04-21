package com.example.healthassistant.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomDigitsService {
    public String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Генерируем строку из случайных цифр
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10); // генерирует число от 0 до 9
            sb.append(digit);
        }

        return sb.toString();
    }
}
