package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.entity.PasswordResetToken;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.exception.PasswordResetException;
import com.carrentalsimple.carrentportal.repository.PasswordResetTokenRepository;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.password-reset.expiration-ms:3600000}") // 1 hour default
    private Long passwordResetTokenExpirationMs;

    @Transactional
    public void initiatePasswordReset(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            // For security, don't reveal if email exists or not
            log.warn("Password reset requested for non-existent email: {}", email);
            return; // Return silently
        }

        User user = userOptional.get();

        // Delete any existing password reset tokens for this user
        passwordResetTokenRepository.findByUser(user).ifPresent(passwordResetTokenRepository::delete);

        // Create new password reset token
        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(Instant.now().plusMillis(passwordResetTokenExpirationMs))
                .used(false)
                .build();

        passwordResetTokenRepository.save(resetToken);

        // Send password reset email
        try {
            emailService.sendPasswordResetEmail(user, resetToken.getToken());
            log.info("Password reset email sent to: {}", email);
        } catch (Exception e) {
            log.error("Failed to send password reset email to: {}", email, e);
            throw new PasswordResetException("Failed to send password reset email");
        }
    }

    @Transactional
    public void resetPassword(String token, String newPassword, String confirmPassword) {
        // Validate passwords match
        if (!newPassword.equals(confirmPassword)) {
            throw new PasswordResetException("Passwords do not match");
        }

        // Find and validate token
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new PasswordResetException("Invalid or expired password reset token"));

        // Check if token is expired
        if (resetToken.getExpiryDate().isBefore(Instant.now())) {
            passwordResetTokenRepository.delete(resetToken);
            throw new PasswordResetException("Password reset token has expired");
        }

        // Check if token is already used
        if (resetToken.isUsed()) {
            throw new PasswordResetException("Password reset token has already been used");
        }

        // Update user password
        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Mark token as used
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        log.info("Password successfully reset for user: {}", user.getEmail());
    }

    public boolean validatePasswordResetToken(String token) {
        Optional<PasswordResetToken> resetTokenOptional = passwordResetTokenRepository.findByToken(token);

        if (resetTokenOptional.isEmpty()) {
            return false;
        }

        PasswordResetToken resetToken = resetTokenOptional.get();
        return !resetToken.getExpiryDate().isBefore(Instant.now()) && !resetToken.isUsed();
    }

    @Transactional
    public void cleanupExpiredTokens() {
        passwordResetTokenRepository.deleteExpiredTokens(Instant.now());
        log.info("Cleaned up expired password reset tokens");
    }
}