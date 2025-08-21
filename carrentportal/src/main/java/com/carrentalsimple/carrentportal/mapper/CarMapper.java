package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.CarRequestDto;
import com.carrentalsimple.carrentportal.dto.CarResponseDto;
import com.carrentalsimple.carrentportal.entity.Car;

public class CarMapper {

    public static CarResponseDto toResponse(Car car){
        if (car == null) return null;
        return CarResponseDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .pricePerDay(car.getPricePerDay())
                .available(car.isAvailable())
                .build();
    }

    public static Car fromRequest(CarRequestDto dto){
        if(dto == null ) return null;
        return Car.builder()
                .brand(dto.getBrand())
                .model(dto.getModel())
                .year(dto.getYear())
                .pricePerDay(dto.getPricePerDay())
                .available(true)
                .build();
    }
    public static void updateEntity(Car car, CarRequestDto dto) {
        if (car == null || dto == null) return;
        car.setBrand(dto.getBrand());
        car.setModel(dto.getModel());
        car.setYear(dto.getYear());
        car.setPricePerDay(dto.getPricePerDay());
        // available is controlled by booking flow; don't set here
    }
}
