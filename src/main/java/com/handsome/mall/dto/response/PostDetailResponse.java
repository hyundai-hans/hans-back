package com.handsome.mall.dto.response;

import com.handsome.mall.dto.ImgDto;
import com.handsome.mall.dto.ProductDto;
import com.handsome.mall.dto.TagDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostDetailResponse {
    private String title;
    private String profileImg;
    private String nickname;
    private String body;
    private Long likesCount;
    private Boolean likeThisPost;
    private LocalDateTime createdAt;
    private List<TagDto> tagList;
    private List<ImgDto> imgList;
    private ProductDto product;
}