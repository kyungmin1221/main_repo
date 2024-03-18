package com.example.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class OrderMenuDto {
    @Getter
    @AllArgsConstructor
    public static class CreateRequest {
        private Integer menuId;
        private int quantity;
        private int totalPrice;
    }

    @Getter
    @AllArgsConstructor
    public static class Menu {
        private Long storeId;
        private Long menuId;
        private String name;
        private int price;
        private String url;
        private String description;
    }
}
