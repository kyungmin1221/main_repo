package com.example.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class StorePageDto {
    @Getter
    @AllArgsConstructor
    @Builder
    public static class Info {
        private final List<StoreDto.SearchResponse> stores;
        private int idx;
        private int totalPage;
        private String category;
    }
}
