package com.example.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class OrderController {
    @GetMapping("/store-main")
    public String registerStoreForm() {
        return "store/store-main";
    }
}
