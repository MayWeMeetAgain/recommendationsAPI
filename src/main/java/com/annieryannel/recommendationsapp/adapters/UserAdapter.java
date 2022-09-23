package com.annieryannel.recommendationsapp.adapters;


import com.annieryannel.recommendationsapp.DTO.RegistrationFormDto;
import com.annieryannel.recommendationsapp.DTO.UserDto;
import com.annieryannel.recommendationsapp.mappers.RegistrationFormMapper;
import com.annieryannel.recommendationsapp.mappers.UserMapper;
import com.annieryannel.recommendationsapp.models.User;
import com.annieryannel.recommendationsapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserAdapter {

    private final UserMapper userMapper;

    private final UserService userService;
    private final RegistrationFormMapper registrationFormMapper;


    public UserDto load(String username) {
        return userMapper.toDto((User)userService.loadUserByUsername(username));
    }

    public UserDto create(RegistrationFormDto userForm) {
        return userMapper.toDto(userService.saveUser(registrationFormMapper.toEntity(userForm)));
    }

    public List<UserDto> loadUsers() {
        return userMapper.UsersToDto(userService.loadAll());
    }
}
