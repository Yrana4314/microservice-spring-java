package com.springbootproject.orderservice.service;

import com.springbootproject.orderservice.model.Order;
import com.springbootproject.orderservice.model.OrderLineItems;
import com.springbootproject.orderservice.model.dto.OrderLineItemsDTO;
import com.springbootproject.orderservice.model.dto.OrderRequest;
import com.springbootproject.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {
    //@Autowired
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest){
        var order = new Order();
        //Map OrderRequest to Order
        order.setOrderNumber(UUID.randomUUID().toString());

        //map OrderItemsList from orderRequest to Order
        //create a orderlineItems list from orderRequest
        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDTOList()
                .stream()
                // can replace following line code with method references i.e. map(this::mapToDto)
                .map(this::mapToDto)
                .toList();
        //set it to order's orderlineItemsList
        order.setOrderLineItemsList(orderLineItems);
        //call Inventory service and place Order
        //check if the product is in stock

        //Save his order into database
        orderRepository.save(order);
    }
    //Create a finction that map OrderLineItemsDTO to orderLineItems
    public OrderLineItems mapToDto(OrderLineItemsDTO orderLineItemsDTO){
        //Create a new OrderLineItems obj
        OrderLineItems orderLineItems = new OrderLineItems();
        //Set the required fields to the orderLineItems obj
        orderLineItems.setSkuCoode(orderLineItemsDTO.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDTO.getPrice());
        orderLineItems.setQuantity(orderLineItemsDTO.getQuantity());
        return orderLineItems;
    }
}
