package com.example.healthassistant.advice;

import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AdviceMapper {
    Advice requestToEntity(AdviceRequestTo entityRequestTo);

    AdviceResponseTo entityToResponse(Advice entity);

    List<AdviceResponseTo> entityToResponse(Iterable<Advice> entities);
}
