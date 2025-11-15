package com.carrentalsimple.carrentportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogoutResponse {
    private String message;
    private boolean success;

    public LogoutResponse(String message) {
        this.message = message;
        this.success = true;
    }
}