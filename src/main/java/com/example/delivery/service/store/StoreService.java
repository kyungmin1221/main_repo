package com.example.delivery.service.store;

import com.example.delivery.domain.User;
import com.example.delivery.dto.StoreDto;

import java.io.IOException;

public interface StoreService {

    void registerStore(StoreDto.CreateRequest requestDto, User user) throws IOException;
}
