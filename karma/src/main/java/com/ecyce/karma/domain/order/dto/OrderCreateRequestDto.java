package com.ecyce.karma.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequestDto {
    @NotNull
    private Long productId;

    @NotNull
    private Long productOptionId;

    @NotBlank
    private String request;

    @NotNull
    private Integer orderCount;
}