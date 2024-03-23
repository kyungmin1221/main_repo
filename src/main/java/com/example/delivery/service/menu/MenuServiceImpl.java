package com.example.delivery.service.menu;

import com.example.delivery.domain.Menu;
import com.example.delivery.exception.CustomException;
import com.example.delivery.exception.ErrorCode;
import com.example.delivery.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{
    private final MenuRepository menuRepository;

    @Override
    public Menu findById(Integer menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_STORE));
    }
}
