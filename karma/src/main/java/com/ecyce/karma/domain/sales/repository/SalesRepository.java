package com.ecyce.karma.domain.sales.repository;

import com.ecyce.karma.domain.sales.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepository extends JpaRepository<Sales , Long> {
}
