package com.example.delivery.service.order;

import com.example.delivery.domain.Order;
import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.OrderMenuDto;

import java.util.List;

public interface OrderService {
    void createOrder(List<OrderMenuDto.MenuRequest> requestDto, String email, Integer storeId);

    OrderDto.Get getOrder(Long orderId);

    List<OrderDto.Get> getOrdersByUserId(String email);

    void updateArrived(Long orderId);

    Order findById(Long orderId);
}
