package com.ecyce.karma.domain.user.dto.response;

import com.ecyce.karma.domain.address.entity.Address;
import com.ecyce.karma.domain.user.dto.request.UserInfoRequest;
import com.ecyce.karma.domain.user.entity.User;

public record UserInfo(
        Long userId ,
        String name,
        String nickname,
        String email ,
        String phoneNumber,
        Long postalCode ,
        String address1,
        String address2,
        String address3

) {

   /* 주소는 처음 저장된 걸 불러옴 */
    public static UserInfo from(User user) {
        Address firstAddress = user.getAddress().isEmpty() ? null : user.getAddress().get(0);

        return new UserInfo(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                firstAddress != null ? firstAddress.getPostalCode() : null,
                firstAddress != null ? firstAddress.getAddress1() : null,
                firstAddress != null ? firstAddress.getAddress2() : null,
                firstAddress != null ? firstAddress.getAddress3() : null
        );
    }


}
