package com._Rbrothers.product_service.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {

    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Long categoryId;
    private Integer availableQuantity;
}