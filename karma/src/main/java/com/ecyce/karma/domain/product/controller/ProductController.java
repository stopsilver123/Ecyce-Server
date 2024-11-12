package com.ecyce.karma.domain.product.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.bookmark.service.BookmarkService;
import com.ecyce.karma.domain.product.dto.response.ProductDetailResponse;
import com.ecyce.karma.domain.product.dto.request.ProductRequestDto;
import com.ecyce.karma.domain.product.service.ProductService;
import com.ecyce.karma.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final BookmarkService bookmarkService;

    /* 상품 등록  , 현재 사진 업로드하는 코드는 포함되어 있지 않음 */
    @PostMapping
    public ResponseEntity<ProductDetailResponse> registerProduct(@AuthUser User user , @RequestBody ProductRequestDto dto){
         ProductDetailResponse response = productService.registerProduct(user  , dto);
         return ResponseEntity.status(HttpStatus.CREATED)
                 .body(response);
    }

   /* 단일 상품 조회 (상세)*/
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable("productId") Long productId){
        ProductDetailResponse dto = productService.getProductDetail(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(dto);
    }

    /* 북마크 등록 */
    @PostMapping("/{productId}/bookmark")
    public ResponseEntity<String> addBookmark(@AuthUser User user, @PathVariable("productId") Long productId) {
        bookmarkService.create(productId, user.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body("북마크가 등록되었습니다.");
    }

    /* 북마크 삭제 */
    @DeleteMapping("/{productId}/bookmark")
    public ResponseEntity<String> deleteBookmark(@AuthUser User user, @PathVariable("productId") Long productId) {
        bookmarkService.delete(productId, user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("북마크가 삭제되었습니다.");
    }

}
