package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;


    @PostMapping("/users/{userId}/cars/{carId}")
    public ResponseEntity<BookingResponseDto> createBooking(@PathVariable Long userId, @PathVariable Long carId, @RequestBody BookingRequestDto bookingRequestDto) {
        return ResponseEntity.ok(bookingService.createBooking(userId, carId, bookingRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<BookingResponseDto>> getBookingByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getBookingByUser(userId));
    }

    @PostMapping("/{booingId}/cancel/{userId}")
    public ResponseEntity<BookingResponseDto> cancelBooking(@PathVariable Long bookingId,

                                                            @PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.cancelBooking(bookingId, userId));

    }

    @PostMapping("/{bookingId}/confirm")
    public ResponseEntity<BookingResponseDto> confirmBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId));


    }

    @PostMapping("/{bookingId}/complete")
    public ResponseEntity<BookingResponseDto> completeBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(bookingService.completeBooking(bookingId));

    }
}