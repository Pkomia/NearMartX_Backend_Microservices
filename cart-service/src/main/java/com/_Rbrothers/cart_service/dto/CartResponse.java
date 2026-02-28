package com._Rbrothers.cart_service.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponse {
    
    private Long cartId;
    private Long userId;
    private String status;
    private List<CartItemResponse>items;
}
