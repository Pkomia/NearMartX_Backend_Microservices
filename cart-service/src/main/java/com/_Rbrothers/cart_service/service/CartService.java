package com._Rbrothers.cart_service.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com._Rbrothers.cart_service.dto.AddToCartRequest;
import com._Rbrothers.cart_service.dto.CartItemResponse;
import com._Rbrothers.cart_service.dto.CartResponse;
import com._Rbrothers.cart_service.entity.Cart;
import com._Rbrothers.cart_service.entity.CartItem;
import com._Rbrothers.cart_service.repository.CartItemRepository;
import com._Rbrothers.cart_service.repository.CartRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private static final String finalStatus = "ACTIVE";

    @Transactional
    public CartResponse addItem(Long userId, AddToCartRequest request){
        
        Cart cart = cartRepository
                    .findByUserIdAndStatus(userId, finalStatus)
                    .orElseGet(()->createNewCart(userId));

        CartItem existingItem = cartItemRepository
                                .findByCartIdAndProductId(cart.getId(), request.getProductId())
                                .orElse(null);

        
        if(existingItem!=null){
            existingItem.setQuantity(existingItem.getQuantity()+request.getQuantity());
            cartItemRepository.save(existingItem);
        }
        else{
            CartItem item = CartItem.builder()
                            .cartId(cart.getId())
                            .productId(request.getProductId())
                            .quantity(request.getQuantity())
                            .createdAt(LocalDateTime.now())
                            .build();
            
            cartItemRepository.save(item);
        }

        return getActiveCart(userId);
    }

    public CartResponse getActiveCart(Long userId){

        Cart cart = cartRepository
                    .findByUserIdAndStatus(userId, finalStatus)
                    .orElseThrow(()-> new RuntimeException("No active cart"));
        
        List<CartItemResponse> items = cartItemRepository.findByCartId(cart.getId())
                                       .stream()
                                       .map(item -> CartItemResponse.builder()
                                                    .id(item.getId())
                                                    .productId(item.getProductId())
                                                    .quantity(item.getQuantity())
                                                    .build())
                                        .toList();

        return CartResponse.builder()
                .cartId(cart.getId())
                .userId(userId)
                .status(cart.getStatus())
                .items(items)
                .build();
    }

    @Transactional
    public void clearCart(Long userId){
        Cart cart = cartRepository
                    .findByUserIdAndStatus(userId, finalStatus)
                    .orElseThrow(()->new RuntimeException("No active cart"));

        cartItemRepository.deleteByCartId(cart.getId());
    }


    private Cart createNewCart(Long userId){
        Cart cart = Cart.builder()
                    .userId(userId)
                    .status(finalStatus)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            
            return cartRepository.save(cart);
    }
    
}
