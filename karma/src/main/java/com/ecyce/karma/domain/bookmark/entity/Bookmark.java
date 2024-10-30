package com.ecyce.karma.domain.bookmark.entity;

import com.ecyce.karma.domain.product.entity.Product;
import com.ecyce.karma.domain.user.entity.User;
import com.ecyce.karma.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Bookmark extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmarkId")
    private Long bookmarkId;

    @ManyToOne
    @JoinColumn(name = "userId" ,updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId" ,updatable = false, nullable = false)
    private Product product;

    @Builder
    public Bookmark(User user , Product product){
        this.user = user;
        this.product = product;
    }
}
