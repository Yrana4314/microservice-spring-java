package com.springbootproject.inventoryservice.controller;

import com.springbootproject.inventoryservice.model.dto.InventoryResponse;
import com.springbootproject.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    //If user would like to place multiple item at once that means many SkuCodes
    //localhost:8082/api/inventory/iphone-13,iphone-14
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
