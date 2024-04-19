package com.example.healthassistant.mapper;

import com.example.healthassistant.jwt.model.DTO.AuthRequestTo;
import com.example.healthassistant.model.entity.Advice;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.request.AdviceRequestTo;
import com.example.healthassistant.model.request.UserRequestTo;
import com.example.healthassistant.model.response.AdviceResponseTo;
import com.example.healthassistant.model.response.UserResponseTo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AdviceMapper {
    Advice requestToEntity(AdviceRequestTo entityRequestTo);

    Advice responseToEntity(AdviceResponseTo entityResponseTo);

    List<User> requestToEntity(Iterable<UserRequestTo> entity);

    AdviceResponseTo entityToResponse(Advice entity);

    List<AdviceResponseTo> entityToResponse(Iterable<Advice> entities);
}
