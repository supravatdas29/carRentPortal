package com.carrentalsimple.carrentportal.service;

import com.carrentalsimple.carrentportal.dto.SellerCreateDto;
import com.carrentalsimple.carrentportal.dto.SellerDto;
import com.carrentalsimple.carrentportal.entity.Seller;
import com.carrentalsimple.carrentportal.exception.ResourceNotFound;
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
    public SellerDto getSellerById(Long id) {
        return sellerRepository.findById(id).map(SellerMapper::toDto).orElseThrow(() -> new ResourceNotFound("No such seller Found"));
    }

    @Override
    public SellerDto updateSellerById(Long id, SellerCreateDto dto) {
        Seller sellerById = sellerRepository.findById(id).orElseThrow(() -> new ResourceNotFound("No such seller Found"+id));

        sellerById.setName(dto.getName());
        sellerById.setEmail(dto.getEmail());
        sellerById.setPhone(dto.getPhone());
        Seller updated = sellerRepository.save(sellerById);
        return SellerMapper.toDto(updated);
    }

    @Override
    public List<SellerDto> getAllSeller() {
        return sellerRepository.findAll().stream().map(SellerMapper::toDto).toList();
    }

    @Override
    public void deleteSeller(Long id) {
        if (!sellerRepository.existsById(id)) {
            throw new ResourceNotFound("No such Seller found");
        }
        sellerRepository.deleteById(id);
    }
}

