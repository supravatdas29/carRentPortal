package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;


    @PostMapping("/users/{userId}/cars/{carId}/bookings")
    public ResponseEntity<BookingResponseDto> createBooking(@PathVariable Long userId, @PathVariable Long carId, @RequestBody BookingRequestDto bookingRequestDto){
        return ResponseEntity.ok(bookingService.createBooking(bookingRequestDto,userId,carId));
    }
}
