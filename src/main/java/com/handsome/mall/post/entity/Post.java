package com.handsome.mall.post.entity;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Post {
    private Long id;
    private Long memberId;
    private String body;
    private String title;
    private LocalDateTime regDate;
}