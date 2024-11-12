package com.ecyce.karma.domain.product.repository;

import com.ecyce.karma.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product , Long> {

    /* 사용자 id로 제품 리스트 반환*/
    @Query("select p from Product  p where p.user.userId =:userId")
    List<Product> findByUserId(@Param("userId") Long userId);


}
