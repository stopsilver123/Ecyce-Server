package com.ecyce.karma.domain.product.repository;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("SELECT o FROM ProductOption o JOIN FETCH o.product WHERE o.product = :product")
    ProductOption findByProductId(@Param("product") Product product);
}