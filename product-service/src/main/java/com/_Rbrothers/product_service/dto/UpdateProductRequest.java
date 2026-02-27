package com._Rbrothers.product_service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer availableQuantity;
    private String status;
}