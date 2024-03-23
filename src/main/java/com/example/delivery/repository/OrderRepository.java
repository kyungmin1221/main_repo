package com.example.delivery.repository;

import com.example.delivery.domain.Order;
import com.example.delivery.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserId(Long userId);

    List<Order> findAllByStoreId(Integer id);
}
