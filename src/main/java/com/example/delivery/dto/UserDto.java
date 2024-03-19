package com.example.delivery.dto;

import com.example.delivery.constant.Role;
import com.example.delivery.domain.User;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserDto {
    private String email;
    private String  nickname;
    private long point;
    private Role role;

    @Builder
    public UserDto(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.point = user.getPoint();
        this.role = user.getRole();
    }
}
