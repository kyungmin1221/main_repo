package com.example.delivery.controller;

import com.example.delivery.global.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

@GetMapping("/")
public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    if (userDetails != null) {
        model.addAttribute("email", userDetails.getUsername());
    } else {
        // userDetails가 null일 때면 ? ->  로그인 페이지로 리디렉션
        return "redirect:/login";
    }
    return "home";
}

}
