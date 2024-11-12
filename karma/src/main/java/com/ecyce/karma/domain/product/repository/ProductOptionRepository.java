package com.ecyce.karma.domain.product.repository;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("select o from ProductOption o where o.product.productId =:productId")
    List<ProductOption> findByProductId(@Param("productId") Long productId);
}