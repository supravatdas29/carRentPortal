package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.SellerCreateDto;
import com.carrentalsimple.carrentportal.dto.SellerDto;
import com.carrentalsimple.carrentportal.entity.Seller;

public class SellerMapper {

    //RequestDto --> Entity
    public static Seller toEntity(SellerCreateDto dto) {
        if (dto == null) return null;

        return Seller.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }

    //Entity - ResponseDto
    public static SellerDto toDto(Seller entity) {
        if (entity == null) return null;

        return SellerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .build();
    }
}
