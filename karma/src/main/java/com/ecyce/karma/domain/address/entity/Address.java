package com.ecyce.karma.domain.address.entity;

import com.ecyce.karma.domain.user.dto.request.UserInfoRequest;
import com.ecyce.karma.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "addressId")
    private Long addressId;

    @Column(nullable = false)
    private String address1; // 도시

    @Column(nullable = false)
    private String address2; // 구

    @Column(nullable = false)
    private String address3; // 건물
    @Column(nullable = false)
    private Long postalCode; //우편번호

    @ManyToOne
    @JoinColumn(name = "userId" ,updatable = false, nullable = false)
    private User user;

    @Builder
    public Address(User user , String address1 , String address2 , String address3 , Long postalCode){
        this.user = user;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.postalCode = postalCode;
    }

    // 주소 형식 포맷팅
    @Override
    public String toString() {
        return String.format("[%d] %s %s %s", postalCode, address1, address2, address3);
    }

    public static Address toEntity(User user , UserInfoRequest request){
        return Address.builder()
                .user(user)
                .postalCode(request.postalCode())
                .address1(request.address1())
                .address2(request.address2())
                .address3(request.address3())
                .build();
    }

    public void setUser(User user1){
        this.user = user1;
    }
}
