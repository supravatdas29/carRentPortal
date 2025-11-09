package com.carrentalsimple.carrentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {
    private String message;
    private boolean success;

    public ForgotPasswordResponse(String message) {
        this.message = message;
        this.success = true;
    }

    public static ForgotPasswordResponse success(String message) {
        return new ForgotPasswordResponse(message, true);
    }

    public static ForgotPasswordResponse error(String message) {
        return new ForgotPasswordResponse(message, false);
    }
}