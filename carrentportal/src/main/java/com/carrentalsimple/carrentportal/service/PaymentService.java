package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.PaymentRequest;
import com.carrentalsimple.carrentportal.dto.PaymentResponse;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;

public interface PaymentService {
    PaymentResponse processPayment(Long bookingId, PaymentRequest request);
    PaymentResponse refundPayment(Long paymentId, Double amount);
    PaymentStatus checkPaymentStatus(String transactionId);

}
