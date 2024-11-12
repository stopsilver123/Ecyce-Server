package com.ecyce.karma.domain.user.dto.response;

import com.ecyce.karma.domain.user.entity.User;

public record ArtistInfoResponse (

         Long userId,
         String nickname,
         String profileImage,
         String phoneNumber,
         String bio,
         float averageRating // 작가가 판 아이템의 평균

){

    public static ArtistInfoResponse from(User user , float averageRating){
        return new ArtistInfoResponse(
                user.getUserId(),
                user.getNickname(),
                user.getProfileImage(),
                user.getPhoneNumber(),
                user.getBio(),
                averageRating
        );
    }
}
