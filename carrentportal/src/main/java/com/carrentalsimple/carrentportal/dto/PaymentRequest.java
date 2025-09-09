package com.carrentalsimple.carrentportal.dto;

import com.carrentalsimple.carrentportal.entity.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Double amount;
    @NotNull(message = "Payment method is required")
    private PaymentMethod method;

    @NotBlank(message = "Currency is required")
    @Builder.Default
    private String currency = "INR";

    // Optional fields for different payment methods
    private String cardToken; // For card payments
    private String upiId; // For UPI payments
    private String walletId; // For wallet payments

    // Customer details
    private String customerEmail;
    private String customerPhone;

    // Additional metadata
    private Map<String, String> metadata;

    // Return URLs for redirect-based payments
    private String successUrl;
    private String cancelUrl;
}
