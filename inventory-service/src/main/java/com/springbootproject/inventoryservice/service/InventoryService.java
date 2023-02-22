package com.springbootproject.inventoryservice.service;

import com.springbootproject.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){

        return inventoryRepository.findBySkuCode(skuCode).isPresent();
    }
}
