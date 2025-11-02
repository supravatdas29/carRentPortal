package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.UserRequestDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.Role;


import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRequestDto req);


    UserResponseDto getUserById(Long id);


    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUser(Long id, UserRequestDto dto);

    void deleteUser(Long id);

    Long getUserIdByEmail(String email);

    User getUserByEmail(String email);


}
