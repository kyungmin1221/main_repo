package com.example.delivery.repository.dslrepository;

import com.example.delivery.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> findStoresWithUsersByCategory(String category);
    List<Store> pagingTest();
    List<Store> findStoresWithUsersByCategories(List<String> categories);

    Page<Store> findAllWithUsers(Pageable pageable);

}

