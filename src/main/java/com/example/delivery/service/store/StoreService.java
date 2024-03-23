package com.example.delivery.service.store;

import com.example.delivery.domain.Store;
import com.example.delivery.domain.User;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.dto.StorePageDto;

import java.io.IOException;
import java.util.List;

public interface StoreService {

    void registerStore(StoreDto.CreateRequest requestDto, User user) throws IOException;

    StoreDto.InfoResponse getStoreInfo(User user);

    Store findStoreId(Integer storeId);

    void menuRegister(StoreDto.CreateMenuRequest requestDto, User user) throws IOException;

    List<StoreDto.MenuInfoResponse> getMenuInfo(User user);

    List<StoreDto.MenuInfoResponse> getMenuInfoByStoreId(Integer storeId);

    StorePageDto.Info searchStoreByCategory(String category, int start, int size);

    StorePageDto.Info searchStoreByKeyword(String keyword, int start, int size);

    StoreDto.InfoResponse getStoreInfo(Integer storeId);
}