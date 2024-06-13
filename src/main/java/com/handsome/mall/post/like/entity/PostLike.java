package com.handsome.mall.post.like.entity;

import lombok.Data;

@Data
public class PostLike {

  private Long postId;
  private Long memberId;
  private boolean isLiked;
}
