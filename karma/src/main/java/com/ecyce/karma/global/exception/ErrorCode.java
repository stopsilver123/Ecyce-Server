package com.ecyce.karma.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Auth
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    FAIL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "로그인 후 이용 가능합니다."), // 로그인X 유저의 요청 OR 토큰 불일치
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."), // 엑세스 토큰 만료
    FAIL_AUTHORIZATION(HttpStatus.FORBIDDEN, "권한이 없는 요청입니다."), // 권한 없는 요청
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR"),// 예상치 못한 에러

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),

    // product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND , "해당하는 제품을 찾을 수 없습니다."),
    INVALID_ACCESS(HttpStatus.BAD_REQUEST , "해당 사용자는 제품 정보를 수정할 수 없습니다."),

    // bookmark
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "북마크가 존재하지 않습니다. 북마크를 등록한 후 취소할 수 있습니다.");

    private final HttpStatus status;
    private final String message;
}
