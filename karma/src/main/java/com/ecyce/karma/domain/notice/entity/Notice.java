package com.ecyce.karma.domain.notice.entity;

import com.ecyce.karma.domain.order.entity.Orders;
import com.ecyce.karma.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noticeId")
    private Long noticeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeType noticeType;

    @ManyToOne
    @JoinColumn(name = "userId" ,updatable = false, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "orderId" ,updatable = false, nullable = false)
    private Orders order;

    @Builder
    public Notice(NoticeType noticeType , User user , Orders order){
        this.noticeType = noticeType;
        this.user = user;
        this.order = order;
    }

}
