package com._Rbrothers.product_service.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com._Rbrothers.product_service.dto.CreateProductRequest;
import com._Rbrothers.product_service.dto.ProductResponse;
import com._Rbrothers.product_service.dto.ReserveRequest;
import com._Rbrothers.product_service.dto.UpdateProductRequest;
import com._Rbrothers.product_service.service.ProductService;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/{id}/reserve")
    public void reserve(@PathVariable Long id,
                        @RequestBody ReserveRequest request) {
        productService.reserve(id, request);
    }

    @PostMapping("/{id}/confirm")
    public void confirm(@PathVariable Long id,
                        @RequestParam Long orderId) {
        productService.confirm(id, orderId);
    }

    @PostMapping("/{id}/release")
    public void release(@PathVariable Long id,
                        @RequestParam Long orderId) {
        productService.release(id, orderId);
    }

    @PostMapping
    public ProductResponse createProduct(
            @RequestBody CreateProductRequest request,
            @RequestHeader("X-User-Id") Long shopkeeperId,
            @RequestHeader("X-User-Roles") String roles) {
 
        if (!roles.contains("SHOPKEEPER")) {
            throw new RuntimeException("Only shopkeepers can create products");
        }

        return productService.createProduct(request, shopkeeperId);
    }

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request,
            @RequestHeader("X-User-Id") Long shopkeeperId,
            @RequestHeader("X-User-Roles") String roles) {

        if (!roles.contains("SHOPKEEPER")) {
            throw new RuntimeException("Only shopkeepers can update products");
        }

        return productService.updateProduct(id, request);
    }
}