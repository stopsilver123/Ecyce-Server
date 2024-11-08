package com.ecyce.karma.domain.user.entity;

import com.ecyce.karma.domain.address.entity.Address;
import com.ecyce.karma.domain.bookmark.entity.Bookmark;
import com.ecyce.karma.domain.notice.entity.Notice;
import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.sales.entity.Sales;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;

    @Column(length = 20 , unique = true) //닉네임 중복 불가
    private String nickname;

    @Column(updatable = false)
    private String email;

    @Column(length = 1024)
    private String profileImage;

    @Column(length = 15 , unique = true)
    private String phoneNumber;

    @Column(length = 200)
    private String bio;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    List<Notice> noticeList = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
    List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "sellerUser", cascade = CascadeType.ALL, orphanRemoval = true)
     List<Orders> userAsSeller = new ArrayList<>();

    @OneToMany(mappedBy = "buyerUser", cascade = CascadeType.ALL, orphanRemoval = true)
     List<Orders> userAsBuyer = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL, orphanRemoval = true)
     List<Sales> sales = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Address address;

    @Builder
    public  User(String nickname , String email , String profileImage , String phoneNumber , String bio){
        this.nickname = nickname;
        this.email  = email;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
    }
}

