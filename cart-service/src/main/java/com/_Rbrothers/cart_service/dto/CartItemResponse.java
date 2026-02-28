package com._Rbrothers.cart_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemResponse {
    
    private Long id;
    private Long productId;
    private Integer quantity;
}
