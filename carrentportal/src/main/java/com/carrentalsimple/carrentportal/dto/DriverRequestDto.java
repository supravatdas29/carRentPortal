package com.carrentalsimple.carrentportal.dto;

import com.carrentalsimple.carrentportal.entity.enums.DriverStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DriverRequestDto {
    private String name;
    private String email;
    private String licenseNumber;
    private String phone;
    private DriverStatus status;

}
