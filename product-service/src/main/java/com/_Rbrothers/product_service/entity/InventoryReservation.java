package com._Rbrothers.product_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long orderId;

    private Integer quantity;

    private String status; // RESERVED, CONFIRMED, RELEASED

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}