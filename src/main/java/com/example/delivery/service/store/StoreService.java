package com.example.delivery.service.store;

import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.dto.StoreDto;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    void registerStore(StoreDto.CreateRequest requestDto, User user) throws IOException;

    StoreDto.InfoResponse getStoreInfo(User user);

    Store findStoreId(Integer storeId);

    void menuRegister(StoreDto.CreateMenuRequest requestDto, User user) throws IOException;

    List<StoreDto.MenuInfoResponse> getMenuInfo(User user);

    StoreDto.InfoResponse getStoreInfo(Integer storeId);
}
