package com.example.delivery.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class UserLoginDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserLoginRequestDto {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }
}
