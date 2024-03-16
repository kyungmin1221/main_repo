package com.example.delivery.service.user;

import com.example.delivery.dto.UserRegisterDto;

public interface UserService {

    UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto);

}
