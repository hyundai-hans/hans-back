package com.handsome.mall.post.history.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostHistory {
    private Long postId;
    private Long memberId;
    private LocalDateTime visitTime;
}