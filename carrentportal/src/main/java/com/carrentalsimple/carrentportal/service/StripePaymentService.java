package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.PaymentRequest;
import com.carrentalsimple.carrentportal.dto.PaymentResponse;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;

public class StripePaymentService implements PaymentService{
    @Override
    public PaymentResponse processPayment(Long bookingId, PaymentRequest request) {
        return null;
    }

    @Override
    public PaymentResponse refundPayment(Long paymentId, Double amount) {
        return null;
    }

    @Override
    public PaymentStatus checkPaymentStatus(String transactionId) {
        return null;
    }
}
