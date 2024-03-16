package com.example.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class OrderMenuDto {
    @Getter
    @AllArgsConstructor
    public static class CreateRequest {
        private Integer menuId;
        private int quantity;
        private int totalPrice;
    }
}
