package com.example.delivery.service.user;


import com.example.delivery.dto.UserRegisterDto;
import com.example.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto) {
        return null;
    }
}
