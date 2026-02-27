package com._Rbrothers.product_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._Rbrothers.product_service.dto.CreateProductRequest;
import com._Rbrothers.product_service.dto.ProductResponse;
import com._Rbrothers.product_service.dto.ReserveRequest;
import com._Rbrothers.product_service.dto.UpdateProductRequest;
import com._Rbrothers.product_service.entity.InventoryReservation;
import com._Rbrothers.product_service.entity.Product;
import com._Rbrothers.product_service.repository.InventoryReservationRepository;
import com._Rbrothers.product_service.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryReservationRepository reservationRepository;

    @Transactional
    public void reserve(Long productId, ReserveRequest request) {

        int updated = productRepository.reserveStock(productId, request.getQuantity());

        if (updated == 0) {
            throw new RuntimeException("Insufficient stock");
        }

        InventoryReservation reservation = InventoryReservation.builder()
                .productId(productId)
                .orderId(request.getOrderId())
                .quantity(request.getQuantity())
                .status("RESERVED")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        reservationRepository.save(reservation);
    }

    @Transactional
    public void confirm(Long productId, Long orderId) {

        InventoryReservation reservation =
                reservationRepository
                        .findByProductIdAndOrderId(productId, orderId)
                        .orElseThrow(() -> new RuntimeException("Reservation not found"));

        productRepository.confirmStock(productId, reservation.getQuantity());

        reservation.setStatus("CONFIRMED");
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    @Transactional
    public void release(Long productId, Long orderId) {

        InventoryReservation reservation =
                reservationRepository
                        .findByProductIdAndOrderId(productId, orderId)
                        .orElseThrow(() -> new RuntimeException("Reservation not found"));

        productRepository.releaseStock(productId, reservation.getQuantity());

        reservation.setStatus("RELEASED");
        reservation.setUpdatedAt(LocalDateTime.now());
        reservationRepository.save(reservation);
    }

    @Transactional
    public ProductResponse createProduct(CreateProductRequest request, Long shopkeeperId) {

        Product product = Product.builder()
                .shopkeeperId(shopkeeperId)
                .sku(request.getSku())
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .imageUrl(request.getImageUrl())
                .categoryId(request.getCategoryId())
                .availableQuantity(request.getAvailableQuantity())
                .reservedQuantity(0)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (request.getName() != null)
            product.setName(request.getName());

        if (request.getDescription() != null)
            product.setDescription(request.getDescription());

        if (request.getPrice() != null)
            product.setPrice(request.getPrice());

        if (request.getImageUrl() != null)
            product.setImageUrl(request.getImageUrl());

        if (request.getAvailableQuantity() != null)
            product.setAvailableQuantity(request.getAvailableQuantity());

        if (request.getStatus() != null)
            product.setStatus(request.getStatus());

        product.setUpdatedAt(LocalDateTime.now());

        return mapToResponse(productRepository.save(product));
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .shopkeeperId(product.getShopkeeperId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .categoryId(product.getCategoryId())
                .availableQuantity(product.getAvailableQuantity())
                .reservedQuantity(product.getReservedQuantity())
                .status(product.getStatus())
                .build();
    }
}