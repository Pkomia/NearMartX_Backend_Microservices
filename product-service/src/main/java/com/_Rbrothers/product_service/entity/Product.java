package com._Rbrothers.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products",
       uniqueConstraints = @UniqueConstraint(columnNames = {"shopkeeper_id", "sku"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopkeeperId;

    private String sku;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private BigDecimal price;

    private String imageUrl;

    private Long categoryId;

    private Integer availableQuantity;

    private Integer reservedQuantity;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}