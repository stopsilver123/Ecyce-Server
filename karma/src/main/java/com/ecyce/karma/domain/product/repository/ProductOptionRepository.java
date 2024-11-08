package com.ecyce.karma.domain.product.repository;

import com.ecyce.karma.domain.product.entity.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}