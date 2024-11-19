package com.ecyce.karma.domain.user.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

public record ModifyInfoRequest(
        JsonNullable<String> name,
        JsonNullable<String> nickname,
        JsonNullable<String> bio,
        JsonNullable<String> phoneNumber

) {

}
