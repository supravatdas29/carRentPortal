package com.carrentalsimple.carrentportal.dto;

import com.carrentalsimple.carrentportal.entity.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role; // CUSTOMER, ADMIN, SELLER
}
