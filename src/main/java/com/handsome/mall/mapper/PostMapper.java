package com.handsome.mall.mapper;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.PostDto;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import com.handsome.mall.entity.primary.PostTag;
import com.handsome.mall.entity.primary.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "member", ignore = true)
    @Mapping(target = "postLikes", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "postTags", source = "postTagList")
    @Mapping(target = "postImages", source = "postImgList")
    Post updatePostDtoToPost(String title,String body,List<PostTag> postTagList, List<PostImg> postImgList);

    @Mapping(target = "postImages", source = "postImgList")
    @Mapping(target = "postTags", source = "postTagList")
    @Mapping(target = "title", source = "createPostDto.title")
    @Mapping(target = "body", source = "createPostDto.body")
    @Mapping(target = "product", source = "product")
    Post createPostDtoToPost(CreatePostDto createPostDto, Product product,
        List<PostTag> postTagList, List<PostImg> postImgList);


    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.member.nickname", target = "nickname")
    @Mapping(source = "thumbNailUrl", target = "thumbNailImgUrl")
    @Mapping(source = "post.createdAt", target = "createdAt")
    FindPostResponse postToFindPostResponse(Post post, String thumbNailUrl);


    default List<FindPostResponse> postsToFindPostResponses(List<Post> posts, @Context String thumbNailUrl) {
        return posts.stream()
                    .map(post -> postToFindPostResponse(post, thumbNailUrl))
                    .collect(Collectors.toList());
    }
}

