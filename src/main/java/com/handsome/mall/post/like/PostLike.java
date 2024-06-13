package com.handsome.mall.post.like;

import lombok.Data;

@Data
public class PostLike {
    private Long postId;
    private Long memberId;
    private Boolean isLiked;
}
