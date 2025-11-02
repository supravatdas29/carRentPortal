package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${notification.mail.from}")
    private String fromAddress;

    @Override
    public void sendWelcomeEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(user.getEmail());
        message.setSubject("Welcome to Car Rental Portal");
        message.setText("Hi " + user.getName() + ",\n\nWelcome to Car Rental Portal! Your account has been created successfully.\n\nRegards,\nCar Rental Team");
        safeSend(message);
    }

    @Override
    public void sendBookingPendingEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(booking.getCustomer().getEmail());
        message.setSubject("Booking Received - Pending Payment");
        message.setText("Hi " + booking.getCustomer().getName() + ",\n\n" +
                "We received your booking for car " + booking.getCar().getBrand() + " " + booking.getCar().getModel() +
                " from " + booking.getStartDate() + " to " + booking.getEndDate() + ".\n" +
                "Total: " + booking.getTotalPrice() + "\n" +
                "Please complete the payment to confirm your booking.\n\nRegards,\nCar Rental Team");
        safeSend(message);
    }

    @Override
    public void sendBookingConfirmedEmail(Booking booking) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(booking.getCustomer().getEmail());
        message.setSubject("Booking Confirmed");
        message.setText("Hi " + booking.getCustomer().getName() + ",\n\n" +
                "Your booking (ID: " + booking.getId() + ") is confirmed.\n" +
                "Car: " + booking.getCar().getBrand() + " " + booking.getCar().getModel() + " (" + booking.getCar().getRegistrationNumber() + ")\n" +
                "Dates: " + booking.getStartDate() + " to " + booking.getEndDate() + "\n" +
                "Total: " + booking.getTotalPrice() + "\n\nRegards,\nCar Rental Team");
        safeSend(message);
    }

    private void safeSend(SimpleMailMessage message) {
        try {
            mailSender.send(message);
        } catch (Exception ex) {
            log.error("Failed to send email: {}", ex.getMessage());
        }
    }
}




