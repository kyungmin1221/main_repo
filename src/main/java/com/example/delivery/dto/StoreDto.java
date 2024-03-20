package com.example.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class StoreDto {

    // 음식점 등록 요청
    @Getter
    @AllArgsConstructor
    public static class CreateRequest {
        private final MultipartFile image;
        private final String name;
        private final String address;
        private final String workTime;
        private final String category;
    }

    // 음식점 정보 응답
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

    // 음식점 메뉴 등록 요청
    @Getter
    @AllArgsConstructor
    public static class CreateMenuRequest {
        private final MultipartFile image;
        private final String name;
        private final String description;
        private final int price;
    }

    // 음식점 메뉴 정보 응답
    @Getter
    @Builder
    public static class MenuInfoResponse {
        private final Integer id;
        private final String name;
        private final int price;
        private final String description;
        private final String imageUrl;

    }
}
