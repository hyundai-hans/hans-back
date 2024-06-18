package com.handsome.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductDto {
    private Long productPrice;
    private String productImg;
    private String productUrl;
    private String productName;
}