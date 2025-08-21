package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.UserCreateDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.UserRole;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.UserMapper;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;


    @Override
    public UserResponseDto createUser(UserCreateDto dto, UserRole role) {
        if (role == UserRole.ADMIN) {
            throw new IllegalArgumentException("Admins cannot be created via API. Insert manually in DB.");
        }
        User user = UserMapper.fromCreate(dto);
        User saveUser = userRepository.save(user);
        return UserMapper.toResponse(saveUser);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return  userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto updateUser(Long id, UserCreateDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // later we hash this

        User updated = userRepository.save(user);
        return UserMapper.toResponse(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFound("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
