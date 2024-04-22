package com.example.healthassistant.user;

import com.example.healthassistant.auth.dto.request.AuthRequestTo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToEntity(UserRequestTo editorRequestTo);

    User dtoToEntity(UserResponseTo userResponseTo);

    List<User> dtoToEntity(Iterable<UserRequestTo> editors);

    UserResponseTo entityToDto(User editor);

    List<UserResponseTo> entityToDto(Iterable<User> editors);

    UserRequestTo authToEntity(AuthRequestTo authRequestTo);

    UserRequestTo userToRequest(User user);
}
