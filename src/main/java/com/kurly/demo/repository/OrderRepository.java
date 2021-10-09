package com.kurly.demo.repository;

import com.kurly.demo.domain.Cart;
import com.kurly.demo.domain.Order;
import com.kurly.demo.domain.OrderGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUserId(Long userId);
}
