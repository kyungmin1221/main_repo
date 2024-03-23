package com.example.delivery.controller;

import com.example.delivery.constant.Role;
import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.security.UserDetailsImpl;
import com.example.delivery.service.order.OrderService;
import com.example.delivery.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j(topic = "StoreController")
@Controller
@RequiredArgsConstructor
@RequestMapping("/stores")
public class   StoreController {

    private final StoreService storeService;
    private final OrderService orderService;

    // 음식점 정보 마이페이지
    @Secured(Role.Authority.CEO)
    @GetMapping
    public String getStoreInfo(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StoreDto.InfoResponse storeInfo = storeService.getStoreInfo(userDetails.getUser());
        model.addAttribute("storeInfo", storeInfo);
        return "store/mypage";
    }

    // 음식점 정보 등록 폼
    @Secured(Role.Authority.CEO)
    @GetMapping("/register")
    public String registerStoreForm() {
        return "store/store-register";
    }

    // 음식점 정보 등록 요청
    @Secured(Role.Authority.CEO)
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerStore(
            @ModelAttribute StoreDto.CreateRequest requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
        storeService.registerStore(requestDto, userDetails.getUser());
        return "redirect:/stores";
    }

    // 음식점 정보 수정 폼
    @Secured(Role.Authority.CEO)
    @GetMapping("/update")
    public String updateStorePage() {
        return "store/store-update";
    }

    // 메뉴 정보 페이지
    @Secured(Role.Authority.CEO)
    @GetMapping("/menus")
    public String menuPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<StoreDto.MenuInfoResponse> menus = storeService.getMenuInfo(userDetails.getUser());
        model.addAttribute("menus", menus);
        return "store/menu/menu-list";
    }

    // 메뉴 등록 폼 페이지
    @Secured(Role.Authority.CEO)
    @GetMapping("/menus/register")
    public String menuRegisterPage() {
        return "store/menu/menu-register";
    }

    // 메뉴 등록 요청
    @Secured(Role.Authority.CEO)
    @PostMapping(value = "/menus/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String menuRegister(
            @ModelAttribute StoreDto.CreateMenuRequest requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {
        storeService.menuRegister(requestDto, userDetails.getUser());
        // return "store/menu/menu-register";
        return "redirect:/stores/menus";
    }

    // 메뉴 수정 폼 페이지
    @Secured(Role.Authority.CEO)
    @GetMapping("/menus/update")
    public String menuUpdatePage() {
        return "store/menu/menu-update";
    }

    // 실시간 주문 현황 페이지
    @Secured(Role.Authority.CEO)
    @GetMapping("/orders")
    public String orderStatusPage(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<OrderDto.StatusResponse> ordersByUserId =
                orderService.getOrdersByUserId(userDetails.getUser());
        model.addAttribute("orders", ordersByUserId);
        return "store/order/order-list";
    }

    // 실시간 주문 현황 페이지 > 주문 내역 보기
    @Secured(Role.Authority.CEO)
    @GetMapping("/orders/{orderId}")
    public String orderDetail(Model model, @PathVariable Long orderId) {
        model.addAttribute("orderDetails", orderService.getOrder(orderId));
        return "store/order/order-detail";
    }


    // 음식점 검색
    @GetMapping("/category/{categoryName}")
    public String searchByCategoryName(Model model, @PathVariable String categoryName,
                                       @RequestParam int page) {
        int size = 5;
        int offset = (page-1);
        model.addAttribute("page",
                storeService.searchStoreByCategory(categoryName, offset, size));
        return "search-store";
    }

    // 음식점 검색
    @GetMapping("/search/{keyword}")
    public String searchByKeyword(Model model, @PathVariable String keyword,
                                  @RequestParam int page) {
        int size = 5;
        int offset = (page-1);
        model.addAttribute("page",
                storeService.searchStoreByKeyword(keyword, offset, size));
        return "search-store";
    }
}
