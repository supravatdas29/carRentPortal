package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.entity.SellerRequest;
import com.carrentalsimple.carrentportal.entity.User;
import com.carrentalsimple.carrentportal.entity.enums.SellerRequestStatus;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
import com.carrentalsimple.carrentportal.mapper.SellerRequestMapper;
import com.carrentalsimple.carrentportal.repository.SellerRequestRepository;
import com.carrentalsimple.carrentportal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerRequestServiceImpl implements SellerRequestService{

    private final UserRepository userRepository;
    private final SellerRequestRepository sellerRequestRepository;

    @Override
    public SellerResponseDto createRequest(String email, SellerRequestDto dto) {
        User seller = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("No Seller Found"+email));


        SellerRequest request = SellerRequestMapper.toEntity(dto,seller);
//        request.setStatus(SellerRequestStatus.PENDING);



        SellerRequest saved = sellerRequestRepository.save(request);
        return SellerRequestMapper.toResponseDto(saved);
    }

    @Override
    public List<SellerResponseDto> getRequestBySeller(Long sellerId) {
        return sellerRequestRepository.findBySeller_Id(sellerId).stream()
                .map(SellerRequestMapper::toResponseDto).toList();
    }

    @Override
    public List<SellerResponseDto> gerRequestByStatus(String status) {
        SellerRequestStatus requestStatus = SellerRequestStatus.valueOf(status.toUpperCase());

        return sellerRequestRepository.findByStatus(requestStatus).stream().map(SellerRequestMapper::toResponseDto).toList();
    }

    @Override
    public SellerResponseDto updateRequestStatus(Long requestId, String status) {
       SellerRequest request = sellerRequestRepository.findById(requestId).orElseThrow(() -> new ResourceNotFound("No such Seller exists"));
       SellerRequestStatus newStatus = SellerRequestStatus.valueOf(status.toUpperCase());
       request.setStatus(newStatus);

       SellerRequest updated = sellerRequestRepository.save(request);

       return SellerRequestMapper.toResponseDto(updated);
    }

    @Override
    public void deleteRequest(Long requestId) {
        if (!sellerRequestRepository.existsById(requestId)){
            throw new ResourceNotFound("No such Seller found");
        }

        sellerRequestRepository.deleteById(requestId);

    }
}
