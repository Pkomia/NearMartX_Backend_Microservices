package com._Rbrothers.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com._Rbrothers.product_service.entity.InventoryReservation;

import java.util.Optional;

public interface InventoryReservationRepository
        extends JpaRepository<InventoryReservation, Long> {

    Optional<InventoryReservation> findByProductIdAndOrderId(Long productId, Long orderId);
}