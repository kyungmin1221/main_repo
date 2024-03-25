package com.example.delivery.dto;

import com.example.delivery.domain.Menu;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MenuDto {
    @Getter
    @AllArgsConstructor
    public static class Get {

        private Integer menuId;

        private String name;

        private int price;

        private String description;

        private String imageUrl;

        @Builder
        public Get(Menu menu){
            this.menuId = menu.getId();
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.description = menu.getDescription();
            this.imageUrl = menu.getImageUrl();
        }
    }
}
