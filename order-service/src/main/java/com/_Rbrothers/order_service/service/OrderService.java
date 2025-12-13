package com._Rbrothers.order_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._Rbrothers.order_service.dto.OrderLineItemsDto;
import com._Rbrothers.order_service.dto.OrderRequest;
import com._Rbrothers.order_service.model.Order;
import com._Rbrothers.order_service.model.OrderLineItems;
import com._Rbrothers.order_service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order newOrder = new Order();

        newOrder.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems>orderLineItems= orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
        newOrder.setOrderLineItems(orderLineItems);

        orderRepository.save(newOrder);
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItem){
         OrderLineItems newOrderOLineItems = new OrderLineItems();
         newOrderOLineItems.setPrice(orderLineItem.getPrice());
         newOrderOLineItems.setQuantity(orderLineItem.getQuantity());
         newOrderOLineItems.setSkuCode(orderLineItem.getSkuCode());

         return newOrderOLineItems;
    }
}
