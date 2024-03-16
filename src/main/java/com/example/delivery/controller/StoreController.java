package com.example.delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/stores")
public class StoreController {

    // 음식점 정보 마이페이지
    @GetMapping
    public String storeMyPage() {
        return "store/mypage";
    }

    // 음식점 정보 등록 폼
    @GetMapping("/register")
    public String registerStoreForm() {
        return "store-register";
    }

    // 메뉴 정보 페이지
    @GetMapping("/menus")
    public String menuPage() {
        return "store/menu/menu-list";
    }

    // 메뉴 등록 페이지
    @GetMapping("/menus/register")
    public String menuRegisterPage() {
        return "store/menu/menu-register";
    }

    // 실시간 주문 현황 페이지
    @GetMapping("/orders")
    public String orderStatusPage() {
        return "store/order/order-list";
    }

}
