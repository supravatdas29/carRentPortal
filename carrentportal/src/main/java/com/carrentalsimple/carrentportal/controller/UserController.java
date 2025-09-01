package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.UserCreateDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.enums.UserRole;
import com.carrentalsimple.carrentportal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // --- CUSTOMER APIs ---
    @PostMapping("/customers")
    public ResponseEntity<UserResponseDto> createCustomer(@Valid @RequestBody UserCreateDto dto) {
        return ResponseEntity.ok(userService.createUser(dto, UserRole.CUSTOMER));
    }

    // --- SELLER APIs --

    // --- COMMON APIs ---
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id,
                                                      @Valid @RequestBody UserCreateDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
