package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.UserCreateDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.enums.UserRole;

import java.util.List;

public interface UserService {
    UserResponseDto createUser(UserCreateDto dto, UserRole role);

    UserResponseDto getUserById(Long id);

    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserCreateDto dto);

    void deleteUser(Long id);
}
