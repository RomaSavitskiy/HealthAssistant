package com.example.healthassistant.mapper;

import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.request.UserRequestTo;
import com.example.healthassistant.model.response.UserResponseTo;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToEntity(UserRequestTo editorRequestTo);
    List<User> dtoToEntity(Iterable<UserRequestTo> editors);

    UserResponseTo entityToDto(User editor);

    List<UserResponseTo> entityToDto(Iterable<User> editors);
}
