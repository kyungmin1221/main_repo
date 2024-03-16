package com.example.delivery.service.user;


import com.example.delivery.constant.Role;
import com.example.delivery.domain.User;
import com.example.delivery.dto.UserRegisterDto;
import com.example.delivery.exception.CustomException;
import com.example.delivery.exception.ErrorCode;
import com.example.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto) {
//        if (!requestDto.getPassword().equals(requestDto.getConfirmPassword())) {
//            throw new CustomException(ErrorCode.WRONG_CONFIRM_PASSWORD);
//        }

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        long point = requestDto.getPoint();
        Role role = requestDto.getRole() == null ? Role.USER : Role.CEO;

        Optional<User> checkUserEmail = userRepository.findByEmail(email);

        if(checkUserEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        if(Role.USER.equals(role)) {
            point = 1_000_000;
        }

        User user = User.builder()
                .email(email)
                .nickname(nickname)
                .password(password)
                .point(point)
                .role(role)
                .build();

        userRepository.save(user);

        return new UserRegisterDto.UserRegisterResponseDto(user);

    }
}
