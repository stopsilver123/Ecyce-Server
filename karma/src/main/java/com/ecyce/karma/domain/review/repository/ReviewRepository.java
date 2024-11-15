package com.ecyce.karma.domain.review.repository;

import com.ecyce.karma.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository  extends JpaRepository<Review , Long> {

    @Query("select r from Review  r where r.orders.orderId =: orderId")
    List<Review> findByOrderId(@Param("orderId") Long orderId);
}
