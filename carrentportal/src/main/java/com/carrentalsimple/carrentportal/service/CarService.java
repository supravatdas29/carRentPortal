package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;

import java.util.List;

public interface CarService {
    CarResponseDto createCar(Long adminId, CarRequestDto requestDto);  // only Admin can add
    CarResponseDto updateCar(Long adminId, Long carId, CarRequestDto requestDto); // only Admin
    void deleteCar(Long adminId, Long carId); // only Admin
    List<CarResponseDto> getAllCars(); // anyone
    CarResponseDto getCarById(Long carId);
}

