package com.example.delivery.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

public class OrderDto {
    @Getter
    @AllArgsConstructor
    public static class CreateRequest {

        @NotBlank
        private Integer userId;

        @NotBlank
        private Integer storeId;

        @NotBlank
        private List<OrderMenuDto.CreateRequest> orderMenus;

        private boolean isArrived;
    }

    @Getter
    @AllArgsConstructor
    public static class CreateResponse {
        private Long orderId;
        private String userName;
        private String storeName;
        private List<OrderMenuDto.CreateRequest> orderMenus;
        private boolean isArrived;
    }

}
