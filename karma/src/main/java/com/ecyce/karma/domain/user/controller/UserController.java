package com.ecyce.karma.domain.user.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.bookmark.dto.BookmarkDto;
import com.ecyce.karma.domain.bookmark.service.BookmarkService;
import com.ecyce.karma.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final BookmarkService bookmarkService;

    /* 회원별 북마크 상품 목록 조회 */
    @GetMapping("/bookmarks")
    public ResponseEntity<List<BookmarkDto>> getBookmarks(@AuthUser User user) {
        List<BookmarkDto> bookmarks = bookmarkService.getUserBookmarks(user.getUserId());
        return ResponseEntity.ok(bookmarks);
    }
}
