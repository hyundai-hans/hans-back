package com.handsome.mall.product.entity;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private Long price;
    private String imgUrl;
    private String url;
}