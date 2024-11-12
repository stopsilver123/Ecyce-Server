package com.ecyce.karma.domain.bookmark.service;

import com.ecyce.karma.domain.bookmark.dto.BookmarkDto;
import com.ecyce.karma.domain.bookmark.entity.Bookmark;
import com.ecyce.karma.domain.bookmark.repository.BookmarkRepository;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.product.repository.ProductRepository;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.repository.UserRepository;
import com.ecyce.karma.global.exception.CustomException;
import com.ecyce.karma.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    /* 북마크 생성 */
    public void create(Long productId, Long userId) {
        User user = getUser(userId);
        Product product = getProduct(productId);

        // 북마크 중복 체크
        if (bookmarkRepository.existsByProductAndUser(product, user)) {
            throw new IllegalStateException("이미 북마크된 상품입니다.");
        }

        // 북마크 생성 및 저장
        Bookmark bookmark = new Bookmark(user, product);
        bookmarkRepository.save(bookmark);
    }

    /* 북마크 삭제 */
    public void delete(Long productId, Long userId) {
        User user = getUser(userId);
        Product product = getProduct(productId);

        // 북마크 존재 여부 확인 후 삭제
        Bookmark bookmark = bookmarkRepository.findByProductAndUser(product, user)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOKMARK_NOT_FOUND));

        bookmarkRepository.delete(bookmark);
    }

    /* 회원별 북마크 목록 조회 */
    @Transactional(readOnly = true)
    public List<BookmarkDto> getUserBookmarks(Long userId) {
        User user = getUser(userId);

        List<Bookmark> bookmarks = bookmarkRepository.findByUser_userId(user.getUserId());
        return bookmarks.stream()
                .map(BookmarkDto::from)
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private Product getProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
    }

}
