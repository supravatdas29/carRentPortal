package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.service.SellerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller-requests")
@RequiredArgsConstructor
public class SellerRequestController {

    private final SellerRequestService sellerRequestService;

    @PostMapping("/{sellerId}")
    public ResponseEntity<SellerResponseDto> createSellerRequest(@PathVariable Long sellerId, @RequestBody SellerRequestDto dto){
        return ResponseEntity.ok(sellerRequestService.createRequest(sellerId,dto));
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<SellerResponseDto>> getRequestsBySeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(sellerRequestService.getRequestBySeller(sellerId));
   }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<SellerResponseDto>> getRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(sellerRequestService.gerRequestByStatus(status));
    }

    @PutMapping("/{requestId}/status/{status}")
    public ResponseEntity<SellerResponseDto> updateRequestStatus(
            @PathVariable Long requestId,
            @PathVariable String status) {
        return ResponseEntity.ok(sellerRequestService.updateRequestStatus(requestId, status));
    }

    @DeleteMapping("/{requestId}")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId) {
        sellerRequestService.deleteRequest(requestId);
        return ResponseEntity.ok("Seller Request deleted successfully!");
    }
}

