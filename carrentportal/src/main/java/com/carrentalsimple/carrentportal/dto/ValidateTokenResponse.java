package com.carrentalsimple.carrentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateTokenResponse {
    private boolean valid;
    private String message;

    public ValidateTokenResponse(boolean valid) {
        this.valid = valid;
        if (!valid) {
            this.message = "Invalid or expired password reset token";
        }
    }

    public static ValidateTokenResponse valid() {
        return new ValidateTokenResponse(true, "Token is valid");
    }

    public static ValidateTokenResponse invalid(String message) {
        return new ValidateTokenResponse(false, message);
    }

    public static ValidateTokenResponse invalid() {
        return new ValidateTokenResponse(false, "Invalid or expired password reset token");
    }
}