package com._Rbrothers.product_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com._Rbrothers.product_service.dto.ProductRequest;
import com._Rbrothers.product_service.dto.ProductResponse;
import com._Rbrothers.product_service.model.Product;
import com._Rbrothers.product_service.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product newProduct = Product.builder()
                             .name(productRequest.getName())
                             .description(productRequest.getDescription())
                             .price(productRequest.getPrice())
                             .build();

        productRepository.save(newProduct);   
        log.info("new product {} added", newProduct.getId());                
    }

    public List<ProductResponse>getAllProducts(){
        List<Product>allProductResponses =  productRepository.findAll();

        return allProductResponses.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
        return ProductResponse.builder()
               .id(product.getId())
               .name(product.getName())
               .description(product.getDescription())
               .price(product.getPrice())
               .build();
    }

}
