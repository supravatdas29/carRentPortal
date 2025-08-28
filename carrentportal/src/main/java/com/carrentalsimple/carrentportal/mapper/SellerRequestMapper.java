package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.entity.Seller;
import com.carrentalsimple.carrentportal.entity.SellerRequest;

public class SellerRequestMapper {

    // DTO -> Entity (for creation)
    public static SellerRequest toEntity(SellerRequestDto dto) {
        if (dto == null) return null;

        SellerRequest request = SellerRequest.builder()
                .carBrand(dto.getCarBrand())
                .carModel(dto.getCarModel())
                .year(dto.getYear())
                .carDetails(dto.getCarDetails())
                .expectedPrice(dto.getExpectedPrice())
                .status(null) // let entity default = PENDING
                .build();

        // map sellerId -> Seller
       

        return request;
    }

    // Entity -> Response DTO
    public static SellerResponseDto toResponseDto(SellerRequest entity) {
        if (entity == null) return null;

        return SellerResponseDto.builder()
                .id(entity.getId())
                .carBrand(entity.getCarBrand())
                .carModel(entity.getCarModel())
                .year(entity.getYear())
                .carDetails(entity.getCarDetails())
                .expectedPrice(entity.getExpectedPrice())
                .status(entity.getStatus())
                .sellerId(entity.getSeller() != null ? entity.getSeller().getId() : null)
                .build();
    }
}
