package com.springbootproject.productservice.controller;

import com.springbootproject.productservice.model.dto.ProductRequest;
import com.springbootproject.productservice.model.dto.ProductResponse;
import com.springbootproject.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "Post a product")//@Operation(summary = "Get a Walgreen by its id")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @Operation(summary="Get All products")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProducts();
    }
    @GetMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get product detail By Id")
    public ProductResponse getProductById(@PathVariable(value = "id") String id){
        return productService.getProductDetail(id);
    }

}
