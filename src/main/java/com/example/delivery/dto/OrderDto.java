package com.example.delivery.dto;

import com.example.delivery.domain.Order;
import com.example.delivery.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDto {
    @Getter
    @AllArgsConstructor
    public static class CreateRequest {

        @NotBlank
        private Integer storeId;

        @NotBlank
        private List<OrderMenuDto.MenuRequest> orderMenus;

        private boolean isArrived;
    }

    @Getter
    @AllArgsConstructor

    public static class Get {
        private Long orderId;
        private UserDto user;
        private String storeName;
        private List<OrderMenuDto.Get> orderMenus;
        private int totalPrice;
        private boolean isArrived;
        @Builder
        public Get(Order order){
            this.orderId = order.getId();
            this.user = UserDto.builder().user(order.getUser()).build();
            this.storeName = order.getStore().getName();
            this.isArrived = order.isArrived();
            this.orderMenus = order.getOrderMenus().stream()
                    .map(OrderMenuDto.Get::new).collect(Collectors.toList());

            this.totalPrice = this.orderMenus.stream()
                        .mapToInt(OrderMenuDto.Get::getTotalPrice)
                        .sum();
        }
    }
}
