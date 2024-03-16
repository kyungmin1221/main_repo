package com.example.delivery.controller;

import com.example.delivery.dto.UserRegisterDto;
import com.example.delivery.exception.CustomException;
import com.example.delivery.service.user.UserService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String showSignUpForm() {
        return "register";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") @Valid  UserRegisterDto.UserRegisterRequestDto requestDto,
                               RedirectAttributes redirectAttributes) {

        try {
            UserRegisterDto.UserRegisterResponseDto responseDto = userService.registerUser(requestDto);
            redirectAttributes.addFlashAttribute("success", "회원가입에 성공했습니다. 로그인해주세요.");
            return "/login"; // 회원가입 성공 후, 로그인 페이지로 리다이렉트
        } catch (CustomException e) {
            String errorMessage = e.getErrorCode().getMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "/register"; // 실패 시, 회원가입 폼으로 다시 리다이렉트
        }

    }


}
