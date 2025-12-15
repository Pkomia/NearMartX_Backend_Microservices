package com._Rbrothers.order_service.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com._Rbrothers.order_service.dto.InventoryResponse;
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
    private final WebClient webClient;

    private static final String baseUri = "http://localhost:8083/api/inventory/in_stock";

    public void placeOrder(OrderRequest orderRequest){
        Order newOrder = new Order();

        newOrder.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems>orderLineItems= orderRequest.getOrderLineItemsDtoList().stream().map(this::mapToDto).toList();
        newOrder.setOrderLineItems(orderLineItems);

        List<String>skuCode =  newOrder.getOrderLineItems().stream()
                               .map(OrderLineItems::getSkuCode)
                               .toList();
        
        //call inventory service if order in stock 
        InventoryResponse[] inventoryResponses = webClient.get()
                     .uri("http://localhost:8083/api/inventory/in_stock", uriBuilder-> uriBuilder.queryParam("skuCode", skuCode).build())
                     .retrieve()
                     .bodyToMono(InventoryResponse[].class)
                     .block();

        boolean allProductInStock = Arrays.stream(inventoryResponses)
        .allMatch(ir -> Boolean.TRUE.equals(ir.getIsInStock()));

        if(allProductInStock){
        orderRepository.save(newOrder);
        }
        else{
            throw new IllegalArgumentException("Product is not in stock, please try again later!");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItem){
         OrderLineItems newOrderOLineItems = new OrderLineItems();
         newOrderOLineItems.setPrice(orderLineItem.getPrice());
         newOrderOLineItems.setQuantity(orderLineItem.getQuantity());
         newOrderOLineItems.setSkuCode(orderLineItem.getSkuCode());

         return newOrderOLineItems;
    }
}
