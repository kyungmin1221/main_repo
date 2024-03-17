package com.example.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class StoreDto {

    @Getter
    @AllArgsConstructor
    public static class CreateRequest {
        private final MultipartFile image;
        private final String name;
        private final String address;
        private final String workTime;
        private final String category;
    }

    @Getter
    @Builder
    public static class InfoResponse {
        private String name;
        private String workTime;
        private String category;
        private String address;
        private float storeScore;
        private int likeCount;
        private double totalSales;
        private String imageUrl;
    }

}
