package com.ecyce.karma.domain.product.repository;

import com.ecyce.karma.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.options WHERE p.productId = :productId")
    Product findByIdWithOptions(@Param("productId") Long productId);
}
