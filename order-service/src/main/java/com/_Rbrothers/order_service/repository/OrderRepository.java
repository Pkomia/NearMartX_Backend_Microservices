package com._Rbrothers.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com._Rbrothers.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
