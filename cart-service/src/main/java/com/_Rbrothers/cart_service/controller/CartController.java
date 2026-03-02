package com._Rbrothers.cart_service.controller;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._Rbrothers.cart_service.dto.AddToCartRequest;
import com._Rbrothers.cart_service.dto.CartResponse;
import com._Rbrothers.cart_service.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    
    private final CartService cartService;

    @PostMapping("/items")
    public CartResponse addItem(
        @RequestHeader("X-User-Id") Long userId,
        @RequestBody @Valid AddToCartRequest request){

            return cartService.addItem(userId, request);
        }
    
    @GetMapping("/active")
    public CartResponse getActiveCart(@RequestHeader("X-User-Id") Long userId) {
        return cartService.getActiveCart(userId);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
    } 
    
    @GetMapping("/health")
    public String healthCheck() {
        return new String("Everything working perfectly Pradeep!");
    }
}
