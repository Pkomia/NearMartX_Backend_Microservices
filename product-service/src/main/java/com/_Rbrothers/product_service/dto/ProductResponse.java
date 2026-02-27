package com._Rbrothers.product_service.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {

    private Long id;
    private Long shopkeeperId;
    private String sku;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Long categoryId;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private String status;
}