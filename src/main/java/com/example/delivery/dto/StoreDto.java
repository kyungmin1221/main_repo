package com.example.delivery.dto;

import lombok.AllArgsConstructor;
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

}
