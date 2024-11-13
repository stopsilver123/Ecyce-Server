package com.ecyce.karma.domain.bookmark.repository;

import com.ecyce.karma.domain.bookmark.entity.Bookmark;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark , Long> {
    List<Bookmark> findByUser_userId(Long userId);
    boolean existsByProductAndUser(Product product, User user);
    Optional<Bookmark> findByProductAndUser(Product product, User user);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            "FROM Bookmark b " +
            "WHERE b.product.productId = :productId AND b.user.userId = :userId")
    Boolean isBookmarked(Long productId, Long userId);
}
