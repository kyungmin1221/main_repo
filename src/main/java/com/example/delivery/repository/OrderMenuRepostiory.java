package com.example.delivery.repository;

import com.example.delivery.domain.OrderMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMenuRepostiory extends JpaRepository<OrderMenu, Long> {
}
