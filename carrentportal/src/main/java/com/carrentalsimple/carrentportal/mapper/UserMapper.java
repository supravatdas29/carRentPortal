package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.UserCreateDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.UserRole;

public class UserMapper {

    // Convert User -> UserResponseDto
    public static UserResponseDto toResponse(User user) {
        if (user == null) return null;
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    // Convert UserCreateDto -> User (role string -> enum)
    public static User fromCreate(UserCreateDto dto) {
        if (dto == null) return null;

        UserRole role = UserRole.CUSTOMER; // default role
        if (dto.getRole() != null) {
            try {
                role = UserRole.valueOf(dto.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                // if invalid string is passed, fallback to CUSTOMER
                role = UserRole.CUSTOMER;
            }
        }

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // TODO: hash later
                .role(role)
                .build();
    }
}
