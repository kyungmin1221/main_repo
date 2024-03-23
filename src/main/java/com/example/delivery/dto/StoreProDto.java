package com.example.delivery.dto;

import lombok.*;

public class StoreProDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class ProDto {
        private String category;
    }
}
