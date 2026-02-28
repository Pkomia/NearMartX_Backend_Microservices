package com._Rbrothers.cart_service.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddToCartRequest {
    
    private Long productId;

    @Min(1)
    private Integer quantity;
}
