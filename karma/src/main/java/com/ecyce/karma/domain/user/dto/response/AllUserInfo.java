package com.ecyce.karma.domain.user.dto.response;

import com.ecyce.karma.domain.address.entity.Address;
import com.ecyce.karma.domain.user.entity.User;

public record AllUserInfo(

        Long userId ,
        String name,
        String nickname,
        String bio,
        String email ,
        String phoneNumber,
        Long postalCode ,
        String address1,
        String address2,
        String address3
) {

    public static AllUserInfo from(User user) {
        Address firstAddress = user.getAddress().isEmpty() ? null : user.getAddress().get(0);

        return new AllUserInfo(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getBio(),
                user.getEmail(),
                user.getPhoneNumber(),
                firstAddress != null ? firstAddress.getPostalCode() : null,
                firstAddress != null ? firstAddress.getAddress1() : null,
                firstAddress != null ? firstAddress.getAddress2() : null,
                firstAddress != null ? firstAddress.getAddress3() : null
        );
    }

}
