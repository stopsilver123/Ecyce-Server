package com.ecyce.karma.domain.user.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.bookmark.dto.BookmarkDto;
import com.ecyce.karma.domain.bookmark.service.BookmarkService;
import com.ecyce.karma.domain.product.dto.response.ProductSimpleResponse;
import com.ecyce.karma.domain.product.service.ProductService;
import com.ecyce.karma.domain.user.dto.response.ArtistInfoResponse;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final BookmarkService bookmarkService;
    private final UserService userService;
    private final ProductService productService;

    /* 회원별 북마크 상품 목록 조회 */
    @GetMapping("/users/bookmarks")
    public ResponseEntity<List<BookmarkDto>> getBookmarks(@AuthUser User user) {
        List<BookmarkDto> bookmarks = bookmarkService.getUserBookmarks(user.getUserId());
        return ResponseEntity.ok(bookmarks);
    }

    /* 작가 정보 조회 */
    @GetMapping("/artist/{userId}")
    public ResponseEntity<ArtistInfoResponse> getArtistInfo(@PathVariable("userId") Long userId){
        ArtistInfoResponse response = userService.getArtistInfo(userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(response);
    }

    /*작가의 작품 리스트 반환 */
    @GetMapping("/artist/{userId}/product")
    public ResponseEntity<List<ProductSimpleResponse>> getProductList(@PathVariable("userId")Long artistId, Optional<User> userOpt){
        User user = userOpt.orElse(null); // user가 없으면 null로 설정
        List<ProductSimpleResponse> dtos = productService.getProductListOfArtist(artistId ,user);

        return ResponseEntity.status(HttpStatus.OK)
                .body(dtos);
    }

    /* 회원 조회 */

}
