package com.ecyce.karma.domain.notice.repository;

import com.ecyce.karma.domain.notice.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository< Notice , Long> {
}
