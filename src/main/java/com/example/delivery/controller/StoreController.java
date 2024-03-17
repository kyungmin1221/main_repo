package com.example.delivery.controller;

import com.example.delivery.dto.StoreDto;
import com.example.delivery.security.UserDetailsImpl;
import com.example.delivery.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j(topic = "StoreController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    // 음식점 정보 마이페이지
    @GetMapping
    public String storeMyPage() {
        return "store/mypage";
    }

    // 음식점 정보 등록 폼
    @GetMapping("/register")
    public String registerStoreForm() {
        return "store/store-register";
    }

    // 음식점 정보 등록 요청
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerStore(
            @ModelAttribute StoreDto.CreateRequest requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
        storeService.registerStore(requestDto, userDetails.getUser());
        return "store/mypage";
    }

    // 음식점 정보 수정 폼
    @GetMapping("/update")
    public String updateStoreForm() {
        return "store/store-update";
    }

    // 메뉴 정보 페이지
    @GetMapping("/menus")
    public String menuPage() {
        return "store/menu/menu-list";
    }

    // 메뉴 등록 폼 페이지
    @GetMapping("/menus/register")
    public String menuRegisterPage() {
        return "store/menu/menu-register";
    }

    // 메뉴 수정 폼 페이지
    @GetMapping("/menus/update")
    public String menuUpdatePage() {
        return "store/menu/menu-update";
    }

    // 실시간 주문 현황 페이지
    @GetMapping("/orders")
    public String orderStatusPage() {
        return "store/order/order-list";
    }

    // 실시간 주문 현황 페이지 > 주문 내역 보기
    @GetMapping("/orders/{orderId}")
    public String orderDetail() {
        return "store/order/order-detail";
    }

}
