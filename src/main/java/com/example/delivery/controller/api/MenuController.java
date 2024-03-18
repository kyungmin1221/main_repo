package com.example.delivery.controller.api;

import com.example.delivery.dto.OrderDto;
import com.example.delivery.dto.OrderMenuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {
    @GetMapping("/stores/{storeId}/menu")
    public ResponseEntity<List<OrderMenuDto.Menu> > emailCheck(@PathVariable Long storeId) {
        List<OrderMenuDto.Menu> menus = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            menus.add(new OrderMenuDto.Menu(1L, (long) i,"자장면",3000,"/img/logo2.png","옛날 자장면"));
        }
        return ResponseEntity.ok(menus);
    }
}
