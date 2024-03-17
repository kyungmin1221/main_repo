package com.example.delivery.service.user;


import com.example.delivery.constant.Role;
import com.example.delivery.domain.User;
import com.example.delivery.dto.UserRegisterDto;
import com.example.delivery.exception.CustomException;
import com.example.delivery.exception.ErrorCode;
import com.example.delivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
    // ADMIN_TOKEN
//    private final String CEO_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    @Value("${app.security.ceoToken}")
    private String CEO_TOKEN;

    @Transactional
    @Override
    public UserRegisterDto.UserRegisterResponseDto registerUser(UserRegisterDto.UserRegisterRequestDto requestDto) {

        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String password = passwordEncoder.encode(requestDto.getPassword());
        long point = requestDto.getPoint();


        Optional<User> checkUserEmail = userRepository.findByEmail(email);

        if(checkUserEmail.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }
        Role role = Role.USER; // 기본적으로 USER 권한 설정

        // CEO 토큰이 입력되었는지 확인
        if (requestDto.getCeoToken() != null && !requestDto.getCeoToken().isEmpty()) {
            if (CEO_TOKEN.equals(requestDto.getCeoToken())) {
                role = Role.CEO; // 토큰이 일치하면 CEO 권한 부여
            } else {
                throw new IllegalArgumentException("토큰이 잘못되었습니다. 다시 입력해주세요."); // 토큰이 일치하지 않으면 예외 발생
            }
        }
        // USER 권한에만 기본 포인트 부여
        if (Role.USER.equals(role)) {
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

    public User findUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }
}
