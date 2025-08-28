package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerCreateDto;
import com.carrentalsimple.carrentportal.dto.SellerDto;
import com.carrentalsimple.carrentportal.entity.Seller;
import com.carrentalsimple.carrentportal.mapper.SellerMapper;
import com.carrentalsimple.carrentportal.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService{

    private final SellerRepository sellerRepository;

    @Override
    public SellerDto registerSeller(SellerCreateDto dto) {
        Seller seller = SellerMapper.toEntity(dto);
        return SellerMapper.toDto(sellerRepository.save(seller));
    }

    @Override
    public List<Seller> getAllSeller() {
        return List.of();
    }
}
