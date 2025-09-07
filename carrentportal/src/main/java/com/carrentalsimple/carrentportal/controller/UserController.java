package com.carrentalsimple.carrentportal.controller;


import com.carrentalsimple.carrentportal.dto.UserRequestDto;
import com.carrentalsimple.carrentportal.dto.UserResponseDto;
import com.carrentalsimple.carrentportal.entity.enums.Role;
import com.carrentalsimple.carrentportal.payload.APIResponse;
import com.carrentalsimple.carrentportal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // --- CUSTOMER APIs ---
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> createCustomer(@Valid @RequestBody UserRequestDto dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }




    // --- COMMON APIs ---
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<APIResponse<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> allUsers = userService.getAllUsers();



            return ResponseEntity.ok(APIResponse.success("All User List", allUsers));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);

        return ResponseEntity.ok(APIResponse.success("User Fetched Successfully",userResponseDto));


    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<APIResponse<UserResponseDto>> updateUser(@PathVariable Long id,
                                                      @Valid @RequestBody UserRequestDto dto) {

        UserResponseDto updateUser = userService.updateUser(id, dto);
        return ResponseEntity.ok(APIResponse.success("User Updated",updateUser));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
