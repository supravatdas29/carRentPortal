package com.carrentalsimple.carrentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordResponse {
    private String message;
    private boolean success;
    private String error;

    public ResetPasswordResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public static ResetPasswordResponse success(String message) {
        return new ResetPasswordResponse(message, true);
    }

    public static ResetPasswordResponse error(String error) {
        ResetPasswordResponse response = new ResetPasswordResponse();
        response.setError(error);
        response.setSuccess(false);
        return response;
    }
}