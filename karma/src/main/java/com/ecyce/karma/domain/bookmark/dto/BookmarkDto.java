package com.ecyce.karma.domain.bookmark.dto;

import com.ecyce.karma.domain.bookmark.entity.Bookmark;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkDto {
    private Long bookmarkId;
    private String productName;
    private int price;
    private int duration;

    public BookmarkDto(Long bookmarkId, String productName, int price, int duration) {
        this.bookmarkId = bookmarkId;
        this.productName = productName;
        this.price = price;
        this.duration = duration;
    }

    public static BookmarkDto from(Bookmark bookmark) {
        return new BookmarkDto(
                bookmark.getBookmarkId(),
                bookmark.getProduct().getProductName(),
                bookmark.getProduct().getPrice(),
                bookmark.getProduct().getDuration()
        );
    }
}

