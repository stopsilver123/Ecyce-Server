package com.ecyce.karma.domain.order.repository;

import com.ecyce.karma.domain.order.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
}
