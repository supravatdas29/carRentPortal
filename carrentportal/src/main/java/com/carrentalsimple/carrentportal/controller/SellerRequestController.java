package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.service.SellerRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller-requests")
@RequiredArgsConstructor
public class SellerRequestController {

    private final SellerRequestService sellerRequestService;

    // Seller creates a request (sellerId comes from JWT)
    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<SellerResponseDto> createSellerRequest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SellerRequestDto dto) {

        // ✅ sellerId from token
        Long sellerId = Long.parseLong(userDetails.getUsername()); // username = userId in JWT
        return ResponseEntity.ok(sellerRequestService.createRequest(sellerId, dto));
    }

    // Seller fetches their own requests
    @GetMapping("/my")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<List<SellerResponseDto>> getMyRequests(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long sellerId = Long.parseLong(userDetails.getUsername());
        return ResponseEntity.ok(sellerRequestService.getRequestBySeller(sellerId));
    }

    // Admin: get requests by status
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<SellerResponseDto>> getRequestsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(sellerRequestService.gerRequestByStatus(status));
    }

    // Admin: update request status
    @PutMapping("/{requestId}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SellerResponseDto> updateRequestStatus(
            @PathVariable Long requestId,
            @PathVariable String status) {
        return ResponseEntity.ok(sellerRequestService.updateRequestStatus(requestId, status));
    }

    // Admin: delete a request
    @DeleteMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRequest(@PathVariable Long requestId) {
        sellerRequestService.deleteRequest(requestId);
        return ResponseEntity.ok("Seller Request deleted successfully!");
    }
}


