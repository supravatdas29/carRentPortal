package com.carrentalsimple.carrentportal.dto;

import com.carrentalsimple.carrentportal.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {
    @NotBlank
    private String name;
    @Email@NotBlank
    private String email;

    @NotBlank
    private String password;

    private Role role;


}
