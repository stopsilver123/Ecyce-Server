package com.ecyce.karma.domain.user.controller;

import com.ecyce.karma.domain.auth.customAnnotation.AuthUser;
import com.ecyce.karma.domain.bookmark.dto.BookmarkDto;
import com.ecyce.karma.domain.bookmark.service.BookmarkService;
import com.ecyce.karma.domain.user.dto.request.UserInfoRequest;
import com.ecyce.karma.domain.user.dto.response.ArtistInfoResponse;
import com.ecyce.karma.domain.user.dto.response.UserInfo;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final BookmarkService bookmarkService;
    private final UserService userService;

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

    /* 사용자 정보 조회 */
    @GetMapping("/user")
    public ResponseEntity<UserInfo> getUserInfo(@AuthUser User user){
        UserInfo userInfo = userService.getUserInfo(user);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userInfo);
    }

    /* 사용자 정보 수정 */


    /* 사용자 탈퇴 */
    /* 새로운 사용자인 경우 관련 정보 저장*/
    @PostMapping("/user")
    public ResponseEntity<UserInfo> saveNewUser(@AuthUser User user ,@RequestBody UserInfoRequest dto){
        UserInfo userInfo = userService.saveNewUser(user , dto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userInfo);
    }

}
