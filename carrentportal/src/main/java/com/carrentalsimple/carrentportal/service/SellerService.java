package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerCreateDto;
import com.carrentalsimple.carrentportal.dto.SellerDto;
import com.carrentalsimple.carrentportal.entity.Seller;

import java.util.List;

public interface SellerService {
    SellerDto registerSeller(SellerCreateDto dto);
    List<Seller> getAllSeller();
}
