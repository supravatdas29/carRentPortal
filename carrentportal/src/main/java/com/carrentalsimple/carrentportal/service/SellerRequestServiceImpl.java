package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.entity.Seller;
import com.carrentalsimple.carrentportal.entity.SellerRequest;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.SellerRequestMapper;
import com.carrentalsimple.carrentportal.repository.SellerRepository;
import com.carrentalsimple.carrentportal.repository.SellerRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerRequestServiceImpl implements SellerRequestService{

    private final SellerRepository sellerRepository;
    private final SellerRequestRepository sellerRequestRepository;

    @Override
    public SellerResponseDto createRequest(Long sellerId, SellerRequestDto dto) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() -> new ResourceNotFound("Seller  not found"));

        SellerRequest request = SellerRequestMapper.toEntity(dto);
        request.setSeller(seller);

        SellerRequest saved = sellerRequestRepository.save(request);
        return SellerRequestMapper.toResponseDto(saved);
    }
}
