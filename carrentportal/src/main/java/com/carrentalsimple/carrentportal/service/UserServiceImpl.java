package com.carrentalsimple.carrentportal.service;


import com.carrentalsimple.carrentportal.dto.UserRequestDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.Role;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.UserMapper;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto req) {
       if (userRepository.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + req.getEmail());
        }

        if (req.getPassword() == null || req.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }


        User user = UserMapper.toEntity(req);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User saveUser = userRepository.save(user);
        return UserMapper.toResponseDto(saveUser);
    }



    @Override
    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFound("User not found with id: " + id));
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return  userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found with id " + id));

        user.setName(req.getName());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }
       user.setRole(req.getRole()); // later we hash this

        User updated = userRepository.save(user);
        return UserMapper.toResponseDto(updated);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFound("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new  ResourceNotFound("User not found with email"+email)).getId();
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFound("User not found with email: " + email));
    }
}
