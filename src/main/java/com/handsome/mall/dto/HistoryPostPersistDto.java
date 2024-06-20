package com.handsome.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class HistoryPostPersistDto {
    private Long memberId;
    private Long postId;
    private String thumbnailImagUrl;
}
