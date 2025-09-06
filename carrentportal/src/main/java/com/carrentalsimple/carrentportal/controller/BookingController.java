// src/main/java/com/carrentalsimple/carrentportal/controller/BookingController.java
package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.service.BookingService;
import com.carrentalsimple.carrentportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    // ✅ Create booking
    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<BookingResponseDto> createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookingRequestDto dto) {

        String email = userDetails.getUsername();
        Long userId = userService.getUserIdByEmail(email);// helper method
        return ResponseEntity.ok(bookingService.createBooking(userId, dto));
    }

    // ✅ View user’s bookings
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        BookingResponseDto response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    // ✅ Cancel booking
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok("Booking cancelled successfully");
    }
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BookingResponseDto>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getAllBookings());
    }


}
