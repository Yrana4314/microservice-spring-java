package com.springbootproject.productservice.service;

import com.springbootproject.productservice.exception.ProductNotFoundException;
import com.springbootproject.productservice.model.Product;
import com.springbootproject.productservice.model.dto.ProductRequest;
import com.springbootproject.productservice.model.dto.ProductResponse;
import com.springbootproject.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor    //to force repository instance to initialize
@Slf4j  //log for the createdProduct
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        //Map ProductRequest Obj to product Obj.
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        //Save product instance into mongodatabase
        productRepository.save(product);

        //Add Log information
        log.info("Product with id : {} is saved ",product.getId());

    }

    public List<ProductResponse> getAllProducts() {
        //Map productResponse class into product class
        List<Product> products = productRepository.findAll();
        //Products.stream().map(product -> mapToProductResponse(products) OR
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse  mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public ProductResponse getProductDetail(String productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent())
        {
            Product pd = product.get();
            return ProductResponse.builder()
                    .id(pd.getId())
                    .name(pd.getName())
                    .description(pd.getDescription())
                    .price(pd.getPrice())
                    .build();
        }else{
            throw new ProductNotFoundException(String.format("getProductDetail- The product with id %d is not present ",productId),404);
        }
    }
}
