package com.ecyce.karma.domain.order.repository;

import com.ecyce.karma.domain.order.entity.Orders;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

    @Query("select o from Orders o where o.product.productId =:productId")
    List<Orders> findByProductId(@Param("productId")Long productId);
}
