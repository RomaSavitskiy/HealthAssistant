package com.example.healthassistant.mapper;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.entity.Weight;
import com.example.healthassistant.model.request.WeightRequestTo;
import com.example.healthassistant.model.response.WeightResponseTo;
import com.example.healthassistant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class WeightMapper {
    private final UserService userService;
    private final UserMapper userMapper;

    public Weight requestToEntity(WeightRequestTo weightRequestTo, User user) {
        return Weight.builder()
                .weight(weightRequestTo.weight())
                .user(user)
                .dateTime(weightRequestTo.dateTime())
                .build();
    }

    public WeightResponseTo entityToResponse(Weight weight) {
        return WeightResponseTo.builder()
                .id(weight.getId())
                .weight(weight.getWeight())
                .localDate(weight.getDateTime())
                .build();
    }

    public Iterable<WeightResponseTo> entityToResponse(Iterable<Weight> weights) {
        return StreamSupport.stream(weights.spliterator(), false)
                .map(weight -> WeightResponseTo.builder()
                        .id(weight.getId())
                        .weight(weight.getWeight())
                        .localDate(weight.getDateTime())
                        .build())
                .collect(Collectors.toList());
    }
}
