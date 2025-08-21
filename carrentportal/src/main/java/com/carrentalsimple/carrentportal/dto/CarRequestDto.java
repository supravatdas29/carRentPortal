package com.carrentalsimple.carrentportal.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarRequestDto {
    @NotBlank
    private String brand;
    @NotBlank
    private String model;
    @Min(1886)
    private int year;
    @Min(0)
    private double pricePerDay;
}
