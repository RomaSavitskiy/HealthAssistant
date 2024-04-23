package com.example.healthassistant.advice;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1.0/advices")
@RequiredArgsConstructor
@Tag(name="Advice Controller", description="Содержит CRUD операции для сущности Advice")
public class AdviceController {
    private final AdviceService adviceServiceImpl;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AdviceResponseTo saveAdvice(@RequestBody AdviceRequestTo adviceRequestTo) {
        return adviceServiceImpl.save(adviceRequestTo);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Iterable<AdviceResponseTo> findAll(AdviceCategory category) {
        return adviceServiceImpl.findAllByCategory(category);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        adviceServiceImpl.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AdviceResponseTo updateById(@PathVariable Long id, AdviceRequestTo adviceRequestTo)
            throws InvocationTargetException, IllegalAccessException {
        return adviceServiceImpl.updateById(id, adviceRequestTo);
    }
}
