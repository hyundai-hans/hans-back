package com.handsome.mall.dto;

import java.sql.Blob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ImgDto {
  private Long imgId;
  private String imgUrl;
  private Boolean isThumbnail;
}
