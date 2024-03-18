package com.example.delivery.controller;

import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.OrderMenuDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping
public class OrderController {
    @GetMapping("/store-main")
    public String registerStoreForm() {
        return "store/store-main";
    }

    @PostMapping("/store-main")
    public String createOrder(@RequestBody List<OrderMenuDto.MenuReq> menus) {
        System.out.println("menus = " + menus);
        return "store/store-main";
    }

}
