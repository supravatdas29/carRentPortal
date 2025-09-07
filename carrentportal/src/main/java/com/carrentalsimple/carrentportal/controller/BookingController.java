package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.BookingRequestDto;
import com.carrentalsimple.carrentportal.dto.BookingResponseDto;
import com.carrentalsimple.carrentportal.payload.APIResponse;
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
    public ResponseEntity<APIResponse<BookingResponseDto>> createBooking(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody BookingRequestDto dto) {

        String email = userDetails.getUsername();
        Long userId = userService.getUserIdByEmail(email);
        BookingResponseDto booking = bookingService.createBooking(userId, dto);

        return ResponseEntity.ok(APIResponse.success("Booking Successful", booking));
    }

    // ✅ View my bookings (Customer)
    @GetMapping("/my-bookings")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<APIResponse<List<BookingResponseDto>>> getMyBookings(
            @AuthenticationPrincipal UserDetails userDetails) {

        String email = userDetails.getUsername();
        Long userId = userService.getUserIdByEmail(email);

        return ResponseEntity.ok(APIResponse.success("My Bookings", bookingService.getBookingByUser(userId)));
    }

    // ✅ View single booking (Admin only)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<BookingResponseDto>> getBookingById(@PathVariable Long id) {
        BookingResponseDto response = bookingService.getBookingById(id);
        return ResponseEntity.ok(APIResponse.success("Booking Found", response));
    }

    // ✅ Cancel booking (Customer)
    @PutMapping("/{id}/cancel")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<APIResponse<String>> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(APIResponse.success("Booking cancelled successfully", "CANCELLED"));
    }

    // ✅ Get all bookings (Admin only)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<BookingResponseDto>>> getAllBookings() {
        return ResponseEntity.ok(APIResponse.success("All Bookings", bookingService.getAllBookings()));
    }
}
