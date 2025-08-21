package com.carrentalsimple.carrentportal.mapper;

import com.carrentalsimple.carrentportal.dto.*;
import com.carrentalsimple.carrentportal.entity.*;

import java.util.stream.Collectors;

public class SellerMapper {

    // Convert Seller Entity -> SellerDto
    public static SellerDto toDto(Seller seller) {
        return SellerDto.builder()
                .id(seller.getId())
                .name(seller.getName())
                .email(seller.getEmail())
                .phone(seller.getPhone())
                .requests(
                        seller.getRequests() != null
                                ? seller.getRequests().stream()
                                .map(SellerMapper::toRequestDto)
                                .collect(Collectors.toList())
                                : null
                )
                .build();
    }

    // Convert SellerCreateDto -> Seller Entity
    public static Seller toEntity(SellerCreateDto dto) {
        return Seller.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .build();
    }

    // Convert SellerRequest -> SellerRequestDto
    public static SellerRequestDto toRequestDto(SellerRequest request) {
        return SellerRequestDto.builder()
                .id(request.getId())
                .carDetails(request.getCarDetails())
                .status(request.getStatus())
                .build();
    }
}
