package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;

public interface SellerRequestService {
    SellerResponseDto createRequest(Long sellerId, SellerRequestDto dto);
}
