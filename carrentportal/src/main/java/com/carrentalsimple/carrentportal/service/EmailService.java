package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.entity.Booking;
import com.carrentalsimple.carrentportal.entity.User;

public interface EmailService {
    void sendWelcomeEmail(User user);
    void sendBookingPendingEmail(Booking booking);
    void sendBookingConfirmedEmail(Booking booking);

    void sendPasswordResetEmail(User user, String resetToken);
}
