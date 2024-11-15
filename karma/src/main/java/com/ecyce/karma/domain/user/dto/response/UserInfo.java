package com.ecyce.karma.domain.user.dto.response;

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

    public static UserInfo from(User user){
        return new UserInfo(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress().getPostalCode(),
                user.getAddress().getAddress1(),
                user.getAddress().getAddress2(),
                user.getAddress().getAddress3()
        );
    }


}
