package com.carrentalsimple.carrentportal.controller;

import com.carrentalsimple.carrentportal.dto.SellerCreateDto;
import com.carrentalsimple.carrentportal.dto.SellerDto;
import com.carrentalsimple.carrentportal.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService sellerService;

    @PostMapping
    public ResponseEntity<SellerDto> createSeller(@RequestBody SellerCreateDto sellerCreateDto){
        SellerDto saveSeller = sellerService.registerSeller(sellerCreateDto);
        return new ResponseEntity<>(saveSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDto> getById(@PathVariable Long id){
        return ResponseEntity.ok(sellerService.getSellerById(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SellerDto> updateById(@PathVariable Long id,@RequestBody SellerCreateDto sellerCreateDto){
        return ResponseEntity.ok(sellerService.updateSellerById(id,sellerCreateDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();

    }
}
