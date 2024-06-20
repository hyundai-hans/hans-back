package com.handsome.mall.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryPersistDto {
    private Long memberId;
    private Long productId;
    private Long viewHistoryCount;
    private String viewHistoryImgUrl;
}
