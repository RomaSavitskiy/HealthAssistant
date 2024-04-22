package com.example.healthassistant.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;

@RestController
@RequestMapping("/api/v1.0/users")
@RequiredArgsConstructor
@Tag(name="User Controller",
        description="Содержит CRUD операции для сущности User")
public class UserController {
    private final UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseTo save(@RequestBody UserRequestTo requestTo) {
		return service.save(requestTo);
    }

    @GetMapping(("/{id}"))
    @ResponseStatus(HttpStatus.OK)
    public UserResponseTo getById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Iterable<UserResponseTo> findAll() {
        return service.findAll();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public UserResponseTo update(@RequestBody UserRequestTo editorRequestTo) throws InvocationTargetException, IllegalAccessException {
        return service.update(editorRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }
}
