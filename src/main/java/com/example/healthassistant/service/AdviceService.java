package com.example.healthassistant.service;

import com.example.healthassistant.exceptions.NotFoundException;
import com.example.healthassistant.mapper.AdviceMapper;
import com.example.healthassistant.model.entity.Advice;
import com.example.healthassistant.model.entity.User;
import com.example.healthassistant.model.enums.AdviceCategory;
import com.example.healthassistant.model.request.AdviceRequestTo;
import com.example.healthassistant.model.response.AdviceResponseTo;
import com.example.healthassistant.repository.AdviceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.lang.reflect.InvocationTargetException;

@Service
@RequiredArgsConstructor
public class AdviceService {
    private final AdviceRepository adviceRepository;
    public final AdviceMapper adviceMapper;

    public AdviceResponseTo save(AdviceRequestTo adviceRequestTo) {
        return adviceMapper.entityToResponse(
                adviceRepository.save(
                        adviceMapper.requestToEntity(adviceRequestTo)
                ));
    }

    public Iterable<AdviceResponseTo> findAllByCategory(AdviceCategory adviceCategory) {
        return adviceMapper.entityToResponse(
                adviceRepository.findAllByCategory(adviceCategory)
        );
    }

    public void deleteById(Long id) {
        adviceRepository.deleteById(id);
    }

    public AdviceResponseTo updateById(Long id, AdviceRequestTo adviceRequestTo)
            throws InvocationTargetException, IllegalAccessException {
        Advice newUser = adviceMapper.requestToEntity(adviceRequestTo);
        Advice oldUser = adviceRepository.findById(id).orElseThrow(
                () -> new NotFoundException(404L, "Advice with this ID is not found"));
        BeanUtils.copyProperties(oldUser, newUser);
        oldUser.setId(id);
        return adviceMapper.entityToResponse(adviceRepository.save(oldUser));
    }
}
