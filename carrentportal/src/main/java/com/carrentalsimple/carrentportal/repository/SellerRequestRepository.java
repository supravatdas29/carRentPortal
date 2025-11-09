package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.SellerRequest;
import com.carrentalsimple.carrentportal.entity.enums.SellerRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface SellerRequestRepository extends JpaRepository<SellerRequest,Long> {
    List<SellerRequest> findByStatus(SellerRequestStatus status);
    List<SellerRequest> findBySeller_Id(Long sellerId);

}
