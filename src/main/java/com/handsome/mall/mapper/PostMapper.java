package com.handsome.mall.mapper;

import com.handsome.mall.dto.PostDto;
import com.handsome.mall.entity.primary.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.thumbnailImgUrl", target = "thumbnailImgUrl")
    @Mapping(source = "post.member.nickname", target = "nickName")
     PostDto toPostDto(Post post);
}
