package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;

import java.util.List;

public interface CarService {
    CarResponseDto addCar(CarRequestDto requestDto);  // only Admin can add
    List<CarResponseDto> getAllCars(); // anyone
    CarResponseDto getCarById(Long id);
    CarResponseDto updateCarById(Long id, CarRequestDto dto);
    void removeCar(Long id);
}

