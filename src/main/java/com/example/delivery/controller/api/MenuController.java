package com.example.delivery.controller.api;

import com.example.delivery.dto.MenuDto;
import com.example.delivery.dto.OrderMenuDto;
import com.example.delivery.service.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final StoreService storeService;

    @GetMapping("/stores/{storeId}/menu")
    public ResponseEntity<List<MenuDto.Get>> emailCheck( @PathVariable Integer storeId) {

        return ResponseEntity.ok(storeService.findStoreId(storeId).getMenus().stream().map(MenuDto.Get::new)
                .collect(Collectors.toList()));
    }
}
