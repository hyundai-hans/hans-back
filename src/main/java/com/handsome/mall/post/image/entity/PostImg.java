package com.handsome.mall.post.image.entity;

import lombok.Data;

@Data
public class PostImg {
  private Long productId;
  private Long memberId;
  private String imgUrl;
  private boolean isThumbnail;
}
