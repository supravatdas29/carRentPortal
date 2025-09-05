package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.entity.User;

import java.util.List;

public interface BookingService {

    BookingResponseDto createBooking(Long userId,BookingRequestDto request);
    BookingResponseDto getBookingById(Long id);
    List<BookingResponseDto> getBookingByUser(Long userId);

    void cancelBooking(Long bookingId);
//    BookingResponseDto confirmBooking(Long bookingId);
//    BookingResponseDto completeBooking(Long bookingId);

    List<BookingResponseDto> getAllBookings();
//    List<BookingResponseDto> getBookingsByCar(Long carId);
}
