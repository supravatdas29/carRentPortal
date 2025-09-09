package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.PaymentRequest;
import com.carrentalsimple.carrentportal.dto.PaymentResponse;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;
import com.carrentalsimple.carrentportal.payload.APIResponse;
import com.carrentalsimple.carrentportal.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ✅ Customer: Process Payment
    @PostMapping("/process/{bookingId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<APIResponse<PaymentResponse>> processPayment(
            @PathVariable Long bookingId,
            @RequestBody PaymentRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        PaymentResponse response = paymentService.processPayment(bookingId, request, userDetails.getUsername());
        return ResponseEntity.ok(APIResponse.success("Payment Successful", response));
    }

    // ✅ Admin: Refund Payment
    @PostMapping("/refund/{paymentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaymentResponse> refundPayment(
            @PathVariable Long paymentId,
            @RequestParam Double amount) {
        return ResponseEntity.ok(paymentService.refundPayment(paymentId, amount));
    }

    // ✅ Customer/Admin: Check Payment Status
    @GetMapping("/status/{transactionId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<PaymentStatus> checkStatus(
            @PathVariable String transactionId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return ResponseEntity.ok(paymentService.checkPaymentStatus(transactionId, userDetails.getUsername(), isAdmin));
    }

    // ✅ Customer/Admin: Get Payment by ID
    @GetMapping("/{paymentId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<PaymentResponse> getPayment(
            @PathVariable Long paymentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return ResponseEntity.ok(paymentService.getPaymentById(paymentId, userDetails.getUsername(), isAdmin));
    }

    // ✅ Customer/Admin: Get Payments by Booking
    @GetMapping("/booking/{bookingId}")
    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByBooking(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal UserDetails userDetails) {

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        return ResponseEntity.ok(paymentService.getPaymentsByBooking(bookingId, userDetails.getUsername(), isAdmin));
    }
}
