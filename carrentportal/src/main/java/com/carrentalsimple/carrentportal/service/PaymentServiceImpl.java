package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.PaymentRequest;
import com.carrentalsimple.carrentportal.dto.PaymentResponse;
import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.Payment;
import com.carrentalsimple.carrentportal.entity.enums.PaymentStatus;
import com.carrentalsimple.carrentportal.mapper.PaymentMapper;
import com.carrentalsimple.carrentportal.repository.BookingRepository;
import com.carrentalsimple.carrentportal.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final EmailService emailService;

    @Override
    public PaymentResponse processPayment(Long bookingId, PaymentRequest request, String authenticatedEmail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // ✅ Ensure only booking owner can pay
        if (!booking.getCustomer().getEmail().equals(authenticatedEmail)) {
            throw new SecurityException("You are not allowed to pay for this booking");
        }

        // Dummy Razorpay/PG response
        String transactionId = "TXN-" + System.currentTimeMillis();
        String gatewayResponse = "SUCCESS";

        Payment payment = PaymentMapper.toEntity(request, booking, transactionId, gatewayResponse);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setPaidAt(LocalDateTime.now());

        Payment saved = paymentRepository.save(payment);

        // mark booking confirmed on payment success
        booking.setStatus(com.carrentalsimple.carrentportal.entity.enums.BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        emailService.sendBookingConfirmedEmail(booking);
        return PaymentMapper.toResponse(saved);
    }

    @Override
    public PaymentResponse refundPayment(Long paymentId, Double amount) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // ✅ Only Admin endpoint should call this
        payment.setStatus(PaymentStatus.REFUNDED);
        payment.setGatewayResponse("Refunded: " + amount);

        Payment updated = paymentRepository.save(payment);
        return PaymentMapper.toResponse(updated);
    }

    @Override
    public PaymentStatus checkPaymentStatus(String transactionId, String authenticatedEmail, boolean isAdmin) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (!isAdmin && !payment.getBooking().getCustomer().getEmail().equals(authenticatedEmail)) {
            throw new SecurityException("You cannot view this transaction status");
        }

        return payment.getStatus();
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId, String authenticatedEmail, boolean isAdmin) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!isAdmin && !payment.getBooking().getCustomer().getEmail().equals(authenticatedEmail)) {
            throw new SecurityException("You cannot access this payment");
        }

        return PaymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponse> getPaymentsByBooking(Long bookingId, String authenticatedEmail, boolean isAdmin) {
        List<Payment> payments = paymentRepository.findByBookingId(bookingId);

        if (payments.isEmpty()) {
            throw new RuntimeException("No payments found for this booking");
        }

        if (!isAdmin && !payments.get(0).getBooking().getCustomer().getEmail().equals(authenticatedEmail)) {
            throw new SecurityException("You cannot view payments for this booking");
        }

        return PaymentMapper.toResponseList(payments);
    }
}
