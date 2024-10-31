package com.ecyce.karma.domain.pay.repository;

import com.ecyce.karma.domain.pay.entity.Pay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<Pay , Long> {
}
