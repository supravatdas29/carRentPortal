package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.DriverRequestDto;
import com.carrentalsimple.carrentportal.dto.DriverResponseDto;
import com.carrentalsimple.carrentportal.entity.Driver;
import com.carrentalsimple.carrentportal.entity.enums.DriverStatus;

public class DriverMapper {

    public static Driver toEntity(DriverRequestDto dto){
        return Driver.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .licenseNumber(dto.getLicenseNumber())
                .phone(dto.getPhone())
                .driverStatus(DriverStatus.AVAILABLE)
                .build();
    }



    public static DriverResponseDto toResponse(Driver driver){
        return DriverResponseDto.builder()
                .id(driver.getId())
                .name(driver.getName())
                .email(driver.getEmail())
                .licenseNumber(driver.getLicenseNumber())
                .phone(driver.getPhone())
                .status(driver.getDriverStatus())
                .build();
    }
}
