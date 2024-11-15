package com.ecyce.karma.domain.auth.jwt;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JwtTokens {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private Long expiresIn;
    private Boolean isNewUser;

    @Builder
    public  JwtTokens(String accessToken ,String refreshToken , String grantType , Long expiresIn , Boolean isNewUser){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
        this.expiresIn = expiresIn;
        this.isNewUser = isNewUser;
    }

    public static JwtTokens of(String accessToken, String refreshToken, String grantType , Long expiresIn , Boolean isNewUser){
        return new JwtTokens(accessToken ,refreshToken, grantType , expiresIn , isNewUser);
    }
}
