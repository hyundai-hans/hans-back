package com.handsome.mall.mapper;

import com.handsome.mall.dto.PostDto;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.member.nickname", target = "nickName")
    @Mapping(target = "thumbnailImgUrl", expression = "java(findThumbnailImgUrl(post))")
    PostDto toPostDto(Post post);

    default String findThumbnailImgUrl(Post post) {
        return post.getPostImages().stream()
                .filter(PostImg::getIsThumbnail)
                .findFirst()
                .map(PostImg::getImgUrl)
                .orElse(null);
    }
}
