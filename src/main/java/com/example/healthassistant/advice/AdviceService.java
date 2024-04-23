package com.example.healthassistant.advice;

import java.lang.reflect.InvocationTargetException;

public interface AdviceService {
    AdviceResponseTo save(AdviceRequestTo adviceRequestTo);

    Iterable<AdviceResponseTo> findAllByCategory(AdviceCategory adviceCategory);

    void deleteById(Long id);

    AdviceResponseTo updateById(Long id, AdviceRequestTo adviceRequestTo)
            throws InvocationTargetException, IllegalAccessException;
}
