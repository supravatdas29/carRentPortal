package com.carrentalsimple.carrentportal.dto;

import com.carrentalsimple.carrentportal.entity.enums.SellerRequestStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerResponseDto {
    private Long id;
    private String carBrand;
    private String carModel;
    private int year;
    private double expectedPrice;
    private SellerRequestStatus status;

    private Long sellerId; // keep reference only, not full entity
}
