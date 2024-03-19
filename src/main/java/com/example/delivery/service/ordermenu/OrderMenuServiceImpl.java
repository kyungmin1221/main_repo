package com.example.delivery.service.ordermenu;

import com.example.delivery.domain.Order;
import com.example.delivery.domain.OrderMenu;
import com.example.delivery.dto.OrderMenuDto;
import com.example.delivery.repository.OrderMenuRepostiory;
import com.example.delivery.service.menu.MenuService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderMenuServiceImpl implements OrderMenuService{
    private final OrderMenuRepostiory orderMenuRepostiory;
    private final MenuService menuService;

    @Override
    @Transactional
    public void createOrderMenu(OrderMenuDto.MenuRequest requestDto, Order order) {

        OrderMenu orderMenu = OrderMenu.builder()
                .quantity(requestDto.getQuantity())
                .totalPrice(requestDto.getTotalPrice())
                .order(order)
                .menu(menuService.findById(requestDto.getMenuId()))
                .build();

        orderMenuRepostiory.save(orderMenu);
    }
}
