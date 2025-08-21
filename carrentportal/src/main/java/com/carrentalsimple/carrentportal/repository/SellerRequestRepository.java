package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.SellerRequest;
import com.carrentalsimple.carrentportal.entity.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRequestRepository extends JpaRepository<SellerRequest,Long> {
    List<SellerRequest> findByStatus(RequestStatus status);
}
