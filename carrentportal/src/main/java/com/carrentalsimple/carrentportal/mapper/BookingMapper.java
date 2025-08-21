// src/main/java/com/carrentalsimple/carrentportal/mapper/BookingMapper.java
package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Car;
import com.carrentalsimple.carrentportal.entity.User;

import java.time.temporal.ChronoUnit;

public class BookingMapper {

    public static Booking toEntity(User user, Car car, BookingRequestDto dto) {
        if (user == null || car == null || dto == null) return null;

        long days = Math.max(1, ChronoUnit.DAYS.between(dto.getStartDate(), dto.getEndDate()));
        double total = days * car.getPricePerDay();

        return Booking.builder()
                .user(user)
                .car(car)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .totalPrice(total)
                .build();
    }

    public static BookingResponseDto toResponse(Booking booking) {
        if (booking == null) return null;
        return BookingResponseDto.builder()
                .id((long) booking.getId())
                .userId(booking.getUser().getId())
                .carId(booking.getCar().getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .totalPrice(booking.getTotalPrice())
                .status(booking.getStatus())
                .build();
    }
}

