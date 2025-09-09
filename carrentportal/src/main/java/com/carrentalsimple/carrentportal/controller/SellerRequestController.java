package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.SellerRequestDto;
import com.carrentalsimple.carrentportal.dto.SellerResponseDto;
import com.carrentalsimple.carrentportal.payload.APIResponse;
import com.carrentalsimple.carrentportal.service.SellerRequestService;
import com.carrentalsimple.carrentportal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seller-requests")
@RequiredArgsConstructor
public class SellerRequestController {

    private final SellerRequestService sellerRequestService;
    private final UserService userService;

    // Seller creates a request (sellerId comes from JWT)
    @PostMapping
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<APIResponse<SellerResponseDto>> createSellerRequest(

            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SellerRequestDto dto) {

        // ✅ sellerId from token
//       String email = userDetails.getUsername(); // username = userId in JWT
        String email = userDetails.getUsername();
        Long sellerId = userService.getUserIdByEmail(email);
        SellerResponseDto request = sellerRequestService.createRequest(email, dto);
        return ResponseEntity.ok(APIResponse.success("Request Successfully Send ",request));
    }

    // Seller fetches their own requests
    @GetMapping("/my")
    @PreAuthorize("hasRole('SELLER')")
    public ResponseEntity<APIResponse<List<SellerResponseDto>>> getMyRequests(
            @AuthenticationPrincipal UserDetails userDetails) {

        Long sellerId = Long.parseLong(userDetails.getUsername());
        List<SellerResponseDto> bySeller = sellerRequestService.getRequestBySeller(sellerId);
        return ResponseEntity.ok(APIResponse.success("Here are Your Request Details ",bySeller));
    }

    // Admin: get requests by status
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<List<SellerResponseDto>>> getRequestsByStatus(@PathVariable String status) {
        List<SellerResponseDto> dtos = sellerRequestService.gerRequestByStatus(status);
        return ResponseEntity.ok(APIResponse.success("Request By Status",dtos));
    }

    // Admin: update request status
    @PutMapping("/{requestId}/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<SellerResponseDto>> updateRequestStatus(
            @PathVariable Long requestId,
            @PathVariable String status) {
        SellerResponseDto dto = sellerRequestService.updateRequestStatus(requestId, status);
        return ResponseEntity.ok(APIResponse.success("Status Update",dto));
    }

    // Admin: delete a request
    @DeleteMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<APIResponse<String>> deleteRequest(@PathVariable Long requestId) {
        sellerRequestService.deleteRequest(requestId);
        return ResponseEntity.ok(APIResponse.success("Deleted","Seller Request deleted successfully!"));
    }
}


