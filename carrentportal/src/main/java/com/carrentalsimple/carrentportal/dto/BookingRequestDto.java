package com.carrentalsimple.carrentportal.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDto {
    @NotNull
    private Long customerId;

    @NotNull
    private Long carId;

    @NotNull@FutureOrPresent
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

}
