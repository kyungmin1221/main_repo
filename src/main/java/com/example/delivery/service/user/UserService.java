package com.example.delivery.service.user;

import com.example.delivery.domain.User;
import com.example.delivery.dto.UserRegisterDto;

public interface UserService {

    UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto);
    User findUserId(Long userId);
    User findUserByEmail(String email);
}
