package com.ecyce.karma.domain.user.dto.request;

public record UserInfoRequest(
        String name,
        String nickname,
        String phoneNumber,
        Long postalCode ,
        String address1,
        String address2
) {
}
