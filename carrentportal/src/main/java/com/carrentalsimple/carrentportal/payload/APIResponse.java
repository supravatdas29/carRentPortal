package com.carrentalsimple.carrentportal.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    public static <T> APIResponse<T> success(String message, T data) {
        return APIResponse.<T>builder()
                .status("SUCCESS")
                .message(message)
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> APIResponse<T> error(String message) {
        return APIResponse.<T>builder()
                .status("ERROR")
                .message(message)
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
