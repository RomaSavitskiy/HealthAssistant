package com.example.healthassistant.controller;


import com.example.healthassistant.model.enums.AdviceCategory;
import com.example.healthassistant.model.request.AdviceRequestTo;
import com.example.healthassistant.model.response.AdviceResponseTo;
import com.example.healthassistant.service.AdviceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1.0/advices")
@RequiredArgsConstructor
@Tag(name="Advice Controller", description="Содержит CRUD операции для сущности Advice")
public class AdviceController {
    private final AdviceService adviceService;

    @PostMapping
    public AdviceResponseTo saveAdvice(@RequestBody AdviceRequestTo adviceRequestTo) {
        return adviceService.save(adviceRequestTo);
    }

    @GetMapping
    public Iterable<AdviceResponseTo> deleteAdvice(AdviceCategory category) {
        return adviceService.findAllByCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        adviceService.deleteById(id);
    }

    @PutMapping("/{id}")
    public AdviceResponseTo updateById(@PathVariable Long id, AdviceRequestTo adviceRequestTo)
            throws InvocationTargetException, IllegalAccessException {
        return adviceService.updateById(id, adviceRequestTo);
    }
}
