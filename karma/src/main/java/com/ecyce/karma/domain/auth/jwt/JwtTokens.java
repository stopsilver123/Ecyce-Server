package com.ecyce.karma.domain.auth.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtTokens {

    private String accessToken;
    private String grantType;
    private Long expiresIn;
    private Boolean isNewUser;

    @Builder
    public  JwtTokens(String accessToken , String grantType , Long expiresIn , Boolean isNewUser){
        this.accessToken = accessToken;
        this.grantType = grantType;
        this.expiresIn = expiresIn;
        this.isNewUser = isNewUser;
    }

    public static JwtTokens of(String accessToken , String grantType , Long expiresIn , Boolean isNewUser){
        return new JwtTokens(accessToken , grantType , expiresIn , isNewUser);
    }
}
