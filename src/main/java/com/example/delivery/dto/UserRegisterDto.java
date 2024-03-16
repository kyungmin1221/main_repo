package com.example.delivery.dto;

import com.example.delivery.constant.Role;
import com.example.delivery.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.apache.catalina.util.Introspection;

public class UserRegisterDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRegisterRequestDto {
        @Email
        @NotBlank
        private String email;

        @NotBlank(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[가-힣a-zA-Z]{2,10}$",message = "닉네임은 한/영 2자이상 8자이하로 입력해주세요")
        private String nickname;

        @NotBlank
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]).{8,15}$", message = "비밀번호는 최소 8자 이상, 15자 이하이며, 적어도 하나의 소문자, 대문자, 숫자, 특수문자를 포함해야 합니다.")
        private String password;

        @NotBlank(message = "재확인 비밀번호를 입력해주세요")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]).{8,15}$", message = "비밀번호 조건 불만족")
        private String confirmPassword;

        @NotBlank
        private long point = 0;

        @NotNull
        private Role role;

    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserRegisterResponseDto {
        private String email;
        private String nickname;
        private Role role;

        public UserRegisterResponseDto(User user) {
            this.email = user.getEmail();
            this.nickname = user.getNickname();
            this.role = user.getRole();
        }
    }
}

