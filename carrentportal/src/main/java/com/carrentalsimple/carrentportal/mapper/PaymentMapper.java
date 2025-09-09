package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.PaymentRequest;
import com.carrentalsimple.carrentportal.dto.PaymentResponse;
import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Payment;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class PaymentMapper {

    // 🔹 Convert Request DTO → Entity (used while creating a payment)
    public static Payment toEntity(PaymentRequest request, Booking booking, String transactionId, String gatewayResponse) {
        return Payment.builder()
                .booking(booking)
                .amount(request.getAmount())
                .method(request.getMethod())
                .status(PaymentStatus.PENDING) // Default: set to PENDING initially
                .transactionId(transactionId)
                .gatewayResponse(gatewayResponse)
                .build();
    }

    // 🔹 Convert Entity → Response DTO (used when sending response back to client)
    public static PaymentResponse toResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .bookingId(payment.getBooking() != null ? payment.getBooking().getId() : null)
                .amount(payment.getAmount())
                .currency("INR") // You can store in entity if needed
                .status(payment.getStatus())
                .method(payment.getMethod())
                .transactionId(payment.getTransactionId())
                .gatewayResponse(payment.getGatewayResponse())
                .gatewayTransactionId(payment.getTransactionId()) // optional alias
                .createdAt(payment.getCreatedAt())
                .paidAt(payment.getPaidAt())
                .metadata(new HashMap<>()) // fill if needed
                .build();
    }

    // 🔹 Convert Response DTO → Entity (useful in tests / webhook updates)
    public static Payment toEntityFromResponse(PaymentResponse response, Booking booking) {
        return Payment.builder()
                .id(response.getPaymentId())
                .booking(booking)
                .amount(response.getAmount())
                .method(response.getMethod())
                .status(response.getStatus())
                .transactionId(response.getTransactionId())
                .gatewayResponse(response.getGatewayResponse())
                .build();
    }

    public static List<PaymentResponse> toResponseList(List<Payment> payments) {
        return payments.stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    // 🔹 List<Response DTO> → List<Entity> (less common, but for completeness)
    public static List<Payment> toEntityListFromResponses(List<PaymentResponse> responses, Booking booking) {
        return responses.stream()
                .map(response -> toEntityFromResponse(response, booking))
                .collect(Collectors.toList());
    }
}
