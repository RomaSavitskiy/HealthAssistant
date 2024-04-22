package com.example.healthassistant.mapper;

import com.example.healthassistant.model.entity.Advice;
import com.example.healthassistant.model.request.AdviceRequestTo;
import com.example.healthassistant.model.response.AdviceResponseTo;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface AdviceMapper {
    Advice requestToEntity(AdviceRequestTo entityRequestTo);

    AdviceResponseTo entityToResponse(Advice entity);

    List<AdviceResponseTo> entityToResponse(Iterable<Advice> entities);
}
