package com.example.delivery.dto;

import com.example.delivery.domain.Order;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

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
    @ToString
    public static class StatusResponse {
        private Long orderId;
        private UserDto user;
        private String storeName;
        private boolean isArrived;
        @Builder
        public StatusResponse(Order order){
            this.orderId = order.getId();
            this.user = UserDto.builder().user(order.getUser()).build();
            this.storeName = order.getStore().getName();
            this.isArrived = order.isArrived();
        }
    }

    @Getter
    @ToString
    public static class DetailResponse {
        private String name;
        private int quantity;
        private int totalPrice;

        @Builder
        public DetailResponse(String name, int quantity, int totalPrice) {
            this.name = name;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }
    }
}
