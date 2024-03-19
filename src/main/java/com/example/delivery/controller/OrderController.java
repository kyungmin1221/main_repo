package com.example.delivery.controller;

import com.example.delivery.dto.OrderMenuDto;
import com.example.delivery.security.UserDetailsImpl;
import com.example.delivery.service.order.OrderService;
import com.example.delivery.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final StoreService storeService;
    @GetMapping("/orders/stores/{storeId}")
    public String registerStoreForm(Model model, @PathVariable Integer storeId) {
        model.addAttribute("store", storeService.findStoreId(storeId));
        return "store/store-main";
    }

    @PostMapping("/orders/stores/{storeId}")
    public String createOrder(@RequestBody List<OrderMenuDto.MenuRequest> menus, @PathVariable Integer storeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.createOrder(menus, userDetails.getUsername(), storeId);
        return "store/order/order-success";
    }

    @GetMapping("/orders/success")
    public String getSuccessPage() {
        return "store/order/order-success";
    }

    @PutMapping("/orders/{orderId}/arrived")
    public String updateArrived(Model model, @PathVariable Long orderId) {
        orderService.updateArrived(orderId);
        return "redirect:store/order/order-list";
    }
}
