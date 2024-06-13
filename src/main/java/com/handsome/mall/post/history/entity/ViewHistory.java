package com.handsome.mall.post.history.entity;

import lombok.Data;

@Data
public class ViewHistory {
    private Long memberId;
    private Long productId;
    private Long count;
    private String imgUrl;
}
