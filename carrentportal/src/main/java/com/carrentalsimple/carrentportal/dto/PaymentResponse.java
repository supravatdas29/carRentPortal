package com.carrentalsimple.carrentportal.dto;

import com.carrentalsimple.carrentportal.entity.enums.PaymentMethod;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long bookingId;
    private Double amount;
    private String currency;
    private PaymentStatus status;
    private PaymentMethod method;
    private String transactionId;

    // For client-side payment completion (Stripe, Razorpay etc.)
    private String clientSecret;
    private String paymentUrl; // For redirect-based payments

    // Gateway specific response
    private String gatewayResponse;
    private String gatewayTransactionId;

    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime paidAt;

    // Error handling
    private String errorMessage;
    private String errorCode;

    // Additional metadata
    private Map<String, Object> metadata;
}
