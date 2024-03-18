package com.example.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
    public static class MenuReq {
        private Long menuId;
        private int quantity;
        private int totalPrice;
    }

    @Getter
    @AllArgsConstructor
    @ToString
    public static class MenuList {
        private List<MenuReq> menus;
    }
}
