package com.handsome.mall.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostHistoryResponse {
    private Long postId;
    private String title;
    private String nickname;

    private String thumbNailImgUrl;
}
