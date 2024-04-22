package com.example.healthassistant.mapper;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Water;
import com.example.healthassistant.model.entity.Weight;
import com.example.healthassistant.model.request.WaterRequestTo;
import com.example.healthassistant.model.request.WeightRequestTo;
import com.example.healthassistant.model.response.WaterResponseTo;
import com.example.healthassistant.model.response.WeightResponseTo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class WaterMapper {
    public Water requestToEntity(WaterRequestTo waterRequestTo, User user) {
        return Water.builder()
                .volume(waterRequestTo.volume())
                .user(user)
                .dateTime(waterRequestTo.dateTime())
                .build();
    }

    public WaterResponseTo entityToResponse(Water water) {
        return WaterResponseTo.builder()
                .volume(water.getVolume())
                .dateTime(water.getDateTime())
                .build();
    }

    public Iterable<WaterResponseTo> entityToResponse(Iterable<Water> waters) {
        return StreamSupport.stream(waters.spliterator(), false)
                .map(water -> WaterResponseTo.builder()
                        .volume(water.getVolume())
                        .dateTime(water.getDateTime())
                        .build())
                .collect(Collectors.toList());
    }
}
