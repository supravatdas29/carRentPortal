package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.UserRequestDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.Role;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static User toEntity(UserRequestDto dto) {
        if (dto == null) return null;

        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword()) // we’ll encode later in service
                .role(dto.getRole())
                .build();
    }

    public static  UserResponseDto toResponseDto(User entity) {
        if (entity == null) return null;

        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .role(entity.getRole())
                .build();
    }
}
