package com.handsome.mall.post.tag.entity;

import lombok.Data;

@Data
public class PostTag {
    private Long id;
    private Long productId;
    private String body;
}