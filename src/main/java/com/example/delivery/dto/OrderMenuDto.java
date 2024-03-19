package com.example.delivery.dto;

import com.example.delivery.domain.OrderMenu;
import lombok.*;

public class OrderMenuDto {
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Menu {
        private Long storeId;
        private Long menuId;
        private String name;
        private int price;
        private String url;
        private String description;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class MenuRequest {
        private Integer menuId;
        private int quantity;
        private int totalPrice;
    }
    @Getter
    @AllArgsConstructor
    @ToString
    public static class Get{
        private Long orderId;
        private MenuDto.Get menu;
        private int quantity;
        private int totalPrice;
        @Builder
        public Get(OrderMenu orderMenu){
            this.orderId = orderMenu.getId();
            this.menu =  MenuDto.Get.builder().menu(orderMenu.getMenu()).build();
            this.quantity = orderMenu.getQuantity();
            this.totalPrice = orderMenu.getTotalPrice();
        }
    }

}
