package com.example.delivery.controller;

import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.OrderMenuDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
public class OrderController {
    @GetMapping("/store-main")
    public String registerStoreForm() {
        return "store/store-main";
    }

    @PostMapping("/store-main")
    public String createOrder(@ModelAttribute("menus") OrderMenuDto.MenuList menus) {
        System.out.println("menus = " + menus);
        return "store/store-main";
    }

}
