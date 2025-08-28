package com.carrentalsimple.carrentportal.repository;

import com.carrentalsimple.carrentportal.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
}
