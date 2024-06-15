package com.handsome.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostDto {
    private Long postId;
    private String title;
    private String thumbnailImgUrl;
    private String nickName;
}
