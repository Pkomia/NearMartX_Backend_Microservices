package com._Rbrothers.cart_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com._Rbrothers.cart_service.entity.CartItem;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    List<CartItem> findByCartId(Long cartId);

    void deleteByCartId(Long cartId);
}
