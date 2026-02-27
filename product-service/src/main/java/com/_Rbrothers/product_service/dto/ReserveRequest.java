package com._Rbrothers.product_service.dto;

import lombok.Data;

@Data
public class ReserveRequest {
    private Long orderId;
    private Integer quantity;
}