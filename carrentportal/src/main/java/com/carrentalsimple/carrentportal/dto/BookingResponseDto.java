package com.carrentalsimple.carrentportal.dto;


import com.carrentalsimple.carrentportal.entity.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private Long carId;
    private String carBrand;
    private String carModel;
    private String carRegistration;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalPrice;
    private BookingStatus status;

}
