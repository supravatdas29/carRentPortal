package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.PaymentRequest;
import com.carrentalsimple.carrentportal.dto.PaymentResponse;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;

import java.util.List;

public interface PaymentService {
    PaymentResponse processPayment(Long bookingId, PaymentRequest request,String authenticatedEmail);
    PaymentResponse refundPayment(Long paymentId, Double amount);
    PaymentStatus checkPaymentStatus(String transactionId, String authenticatedEmail, boolean isAdmin);
    PaymentResponse getPaymentById(Long paymentId,String authenticatedEmail, boolean isAdmin);
    List<PaymentResponse> getPaymentsByBooking(Long bookingId,String authenticatedEmail, boolean isAdmin);

}
