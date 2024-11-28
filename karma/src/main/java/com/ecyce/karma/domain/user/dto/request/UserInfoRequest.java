package com.ecyce.karma.domain.user.dto.request;

public record UserInfoRequest(
        String name,
        String nickname,
        String phoneNumber,
        String postalCode ,
        String address1,
        String address2
) {
}
