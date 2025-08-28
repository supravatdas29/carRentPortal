package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;

import java.util.List;

public interface SellerRequestService {
    SellerResponseDto createRequest(Long sellerId, SellerRequestDto dto);
    List<SellerResponseDto> getRequestBySeller(Long sellerId);
    List<SellerResponseDto> gerRequestByStatus(String status);
    SellerResponseDto updateRequestStatus(Long requestId,String status);
    void deleteRequest(Long requestId);

}
