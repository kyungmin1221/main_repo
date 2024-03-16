package com.example.delivery.security;


import com.example.delivery.constant.Role;
import com.example.delivery.dto.UserLoginDto;
import com.example.delivery.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/user/login");
        super.setUsernameParameter("email");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDto.UserLoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), UserLoginDto.UserLoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getEmail(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


    // 달라진 부분 - addCookie 하지않는다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, JsonProcessingException {
        String username = ((UserDetailsImpl) authResult.getPrincipal()).getUsername();
        Role role = ((UserDetailsImpl) authResult.getPrincipal()).getUser().getRole();

        // 토큰을 생성
        String token = jwtUtil.createToken(username, role);
        // 데이터를 헤더부분에 담을 수 있는 코드
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

        // 로그인 성공 메시지를 JSON 형태로 응답 본문에 추가
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        // 로그인 응답 메시지 설정
        Map<String, String> successMessage = new HashMap<>();
        successMessage.put("message", "회원 로그인에 성공");
        successMessage.put("token", token);     // 토큰 포함 (편의상)

        String jsonResponse = new ObjectMapper().writeValueAsString(successMessage);
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 필요한 오류 직접 추가
        log.info("로그인 실패");
        response.setStatus(401);
    }

}
