package com.example.delivery.repository.dslrepository;

import com.example.delivery.domain.Store;
import com.example.delivery.dto.StoreDto;
import com.example.delivery.dto.StorePageDto;
import com.example.delivery.dto.StoreProDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.awt.*;
import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findStoresWithUsersByCategory(String category);

    List<Store> findStoresWithUsersByCategories(List<String> categories);

    Page<Store> findAllWithUsers(Pageable pageable);

    List<Store> sort();

    List<Tuple> tupleTest(String name, Integer id);

    List<StoreDto.InfoResponse> findDtoTest(String category);


    Page<Store> searchByCategoryWithPaging(String category, Pageable pageable);

    Page<StoreProDto.ProDto> testProjection(String category, Pageable pageable);

}

