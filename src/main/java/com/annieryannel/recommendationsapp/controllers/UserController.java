package com.annieryannel.recommendationsapp.controllers;

import com.annieryannel.recommendationsapp.DTO.RegistrationFormDto;
import com.annieryannel.recommendationsapp.DTO.UserDto;
import com.annieryannel.recommendationsapp.adapters.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserAdapter userAdapter;

    @GetMapping("/users/{username}")
    public UserDto loadUserByUsername(@PathVariable("username") String username) {
        return userAdapter.load(username);
    }

    @PostMapping("/users")
    public UserDto createUser(@Valid @RequestBody final RegistrationFormDto userForm) {
        return userAdapter.create(userForm);
    }

    @GetMapping("/users")
    public List<UserDto> loadUsers() {
        return userAdapter.loadUsers();
    }
}
