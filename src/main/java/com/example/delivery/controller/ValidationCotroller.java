package com.example.delivery.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidationCotroller {

    @PostMapping("/email-check")
    public ResponseEntity<Boolean> emailCheck(@RequestParam String email) {
        return ResponseEntity.ok(false);
    }

    @PostMapping("/nickname-check")
    public ResponseEntity<Boolean> nicknameCheck(@RequestParam String nickname) {
        return ResponseEntity.ok(false);
    }
}
