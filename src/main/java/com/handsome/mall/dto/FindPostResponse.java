package com.handsome.mall.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class FindPostResponse {

  private Long postId;
  private String title;
  private String nickname;
  private String thumbNailImgUrl;
  private LocalDateTime createdAt;
}
