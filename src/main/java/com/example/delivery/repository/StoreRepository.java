package com.example.delivery.repository;

import com.example.delivery.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    Optional<Store> findByUserId(Long id);
}
