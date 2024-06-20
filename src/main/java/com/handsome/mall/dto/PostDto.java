package com.handsome.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PostDto {
    private Long postId;
    private String title;
    private String thumbNailImagUrl;
    private String nickname;
}
