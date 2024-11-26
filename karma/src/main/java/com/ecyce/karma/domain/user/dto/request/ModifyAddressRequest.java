package com.ecyce.karma.domain.user.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

public record ModifyAddressRequest(
        JsonNullable<Long> postalCode,
        JsonNullable<String> address1,
        JsonNullable<String> address2
) {
}
