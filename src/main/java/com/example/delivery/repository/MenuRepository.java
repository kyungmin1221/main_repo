package com.example.delivery.repository;

import com.example.delivery.domain.Menu;
import com.example.delivery.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

}
