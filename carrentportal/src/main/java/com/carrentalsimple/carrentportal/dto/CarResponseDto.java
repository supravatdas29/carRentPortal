package com.carrentalsimple.carrentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private String fuelType;
    private int year;
    private double pricePerDay;
    private boolean available;
    private boolean withDriverAvailable;
    private boolean selfDriveAvailable;
}
