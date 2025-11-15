package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.AuthResponse;
import com.carrentalsimple.carrentportal.dto.ForgotPasswordRequest;
import com.carrentalsimple.carrentportal.dto.ForgotPasswordResponse;
import com.carrentalsimple.carrentportal.dto.LoginRequest;
import com.carrentalsimple.carrentportal.dto.LogoutResponse;
import com.carrentalsimple.carrentportal.dto.RegisterRequest;
import com.carrentalsimple.carrentportal.dto.ResetPasswordRequest;
import com.carrentalsimple.carrentportal.dto.ResetPasswordResponse;
import com.carrentalsimple.carrentportal.dto.TokenRefreshRequest;
import com.carrentalsimple.carrentportal.dto.ValidateTokenResponse;
import com.carrentalsimple.carrentportal.entity.RefreshToken;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.exception.PasswordResetException;
import com.carrentalsimple.carrentportal.exception.TokenRefreshException;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import com.carrentalsimple.carrentportal.service.EmailService;
import com.carrentalsimple.carrentportal.security.JwtService;
import com.carrentalsimple.carrentportal.service.PasswordResetService;
import com.carrentalsimple.carrentportal.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final RefreshTokenService refreshTokenService;
    private final PasswordResetService passwordResetService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.info("Registering user");
            return ResponseEntity.badRequest().body(new AuthResponse("Email already registered"));
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        user = userRepository.save(user);
        emailService.sendWelcomeEmail(user);

        String accessToken = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("User Login");

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(request.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtService.generateToken(user.getEmail());
                    return ResponseEntity.ok(new AuthResponse(token, requestRefreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logoutUser(@Valid @RequestBody TokenRefreshRequest logOutRequest) {
        refreshTokenService.deleteByToken(logOutRequest.getRefreshToken());
        return ResponseEntity.ok(new LogoutResponse("Log out successful!"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ForgotPasswordResponse> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        log.info("Password reset requested for email: {}", request.getEmail());

        try {
            passwordResetService.initiatePasswordReset(request.getEmail());
            return ResponseEntity.ok(ForgotPasswordResponse.success(
                    "If the email exists in our system, you will receive a password reset link shortly."));
        } catch (PasswordResetException e) {
            log.error("Failed to initiate password reset", e);
            return ResponseEntity.internalServerError().body(
                    ForgotPasswordResponse.error("Failed to send password reset email. Please try again later."));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        log.info("Password reset attempted with token");

        try {
            passwordResetService.resetPassword(request.getToken(), request.getNewPassword(), request.getConfirmPassword());
            return ResponseEntity.ok(ResetPasswordResponse.success(
                    "Password has been reset successfully. You can now login with your new password."));
        } catch (PasswordResetException e) {
            log.error("Password reset failed: {}", e.getMessage());
            return ResponseEntity.badRequest().body(ResetPasswordResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/validate-reset-token")
    public ResponseEntity<ValidateTokenResponse> validateResetToken(@RequestParam String token) {
        boolean isValid = passwordResetService.validatePasswordResetToken(token);

        if (isValid) {
            return ResponseEntity.ok(ValidateTokenResponse.valid());
        } else {
            return ResponseEntity.ok(ValidateTokenResponse.invalid());
        }
    }
}
