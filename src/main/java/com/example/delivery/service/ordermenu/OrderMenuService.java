package com.example.delivery.service.ordermenu;

import com.example.delivery.domain.Order;
import com.example.delivery.domain.OrderMenu;
import com.example.delivery.dto.OrderMenuDto;

public interface OrderMenuService {

    void createOrderMenu(OrderMenuDto.MenuRequest requestDto, Order order);
}
