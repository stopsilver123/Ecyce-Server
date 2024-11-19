package com.ecyce.karma.domain.user.dto.request;

public record ModifyInfoRequest(

        String name,
        String nickname,
        String bio,
        String phoneNumber

) {
}
