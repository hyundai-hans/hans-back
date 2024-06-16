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
public class CreatePostDto {
    private String title;
    private String body;
    private List<String> tagList;
    private String productName;
    private List<String> imgUrl;


    @Override
    public String toString() {
        return "CreatePostDto{" +
            "title='" + title + '\'' +
            ", body='" + body + '\'' +
            ", tagList=" + tagList +
            ", productName='" + productName + '\'' +
            ", imgUrl=" + imgUrl +
            '}';
    }
}
