package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.entity.User;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(Long userId,Long carId,BookingRequestDto bookingRequestDto);
    BookingResponseDto getBookingById(Long id);
    List<BookingResponseDto> getBookingByUser(Long userId);
}
