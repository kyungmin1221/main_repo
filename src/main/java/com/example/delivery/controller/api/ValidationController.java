package com.example.delivery.controller.api;


import com.example.delivery.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidationController {
    private final UserService userService;

    @PostMapping("/email-check")
    public ResponseEntity<Boolean> emailCheck(@RequestParam String email) {
        return ResponseEntity.ok(false);
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<Boolean> nicknameCheck(@RequestParam String nickname) {
        return ResponseEntity.ok(false);
    }
}
