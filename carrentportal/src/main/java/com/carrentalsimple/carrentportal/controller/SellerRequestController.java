package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.service.SellerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller-requests")
@RequiredArgsConstructor
public class SellerRequestController {

    private final SellerRequestService sellerRequestService;

    @PostMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDto> createSellerRequest(@PathVariable Long sellerId, @RequestBody SellerRequestDto dto){
        return ResponseEntity.ok(sellerRequestService.createRequest(sellerId,dto));
    }
}
