package com.handsome.mall.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdatePostDto {

  private Long postId;
  private String title;
  private String body;
  private List<TagDto> tagList;
  private List<ImgDto> imgList;

}
