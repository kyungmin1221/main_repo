package com.example.delivery.controller;

import com.example.delivery.dto.UserRegisterDto;
import com.example.delivery.exception.CustomException;
import com.example.delivery.jwt.JwtUtil;
import com.example.delivery.security.UserDetailsImpl;
import com.example.delivery.service.KakaoService;
import com.example.delivery.service.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Slf4j(topic = "USER_CONTROLLER")
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;

    @GetMapping("/user/signup")
    public String showSignUpForm() {
        return "register";
    }

    @PostMapping("/user/signup")
    public String registerUser(@ModelAttribute("user") @Valid  UserRegisterDto.UserRegisterRequestDto requestDto,
                               RedirectAttributes redirectAttributes) {

        try {
            UserRegisterDto.UserRegisterResponseDto responseDto = userService.registerUser(requestDto);
            redirectAttributes.addFlashAttribute("success", "회원가입에 성공했습니다. 로그인해주세요.");
            return "login"; // 회원가입 성공 후, 로그인 페이지로 리다이렉트
        } catch (CustomException e) {
            String errorMessage = e.getErrorCode().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "register"; // 실패 시, 회원가입 폼으로 다시 리다이렉트
        }

    }

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        String token = kakaoService.kakaoLogin(code);   // jwt 반환
        log.info(token);
        // Cookie 생성 및 직접 브라우저에 Set

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,"Bearer%20" +  token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "home";
    }

    @GetMapping("/user/mypage")
    public String showMyPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("user", userDetails.getUser());
        return "user-mypage";
    }
}
