package com.handsome.mall.mapper;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.ImgDto;
import com.handsome.mall.dto.PostDto;
import com.handsome.mall.dto.ProductDto;
import com.handsome.mall.dto.TagDto;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import com.handsome.mall.entity.primary.PostLike;
import com.handsome.mall.entity.primary.PostTag;
import com.handsome.mall.entity.primary.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.title", target = "title")
    @Mapping(source = "post.member.nickname", target = "nickName")
    @Mapping(target = "thumbNailImagUrl", expression = "java(findThumbnailImgUrl(post))")
    PostDto toPostDto(Post post);



    default String findThumbnailImgUrl(Post post) {
        return post.getPostImages().stream()
            .filter(PostImg::getIsThumbnail)
            .findFirst()
            .map(PostImg::getImgUrl)
            .orElse(null);
    }


    @Mapping(source = "updatePostDto.title", target = "title")
    @Mapping(source = "updatePostDto.body", target = "body")
    Post updatePostDtoToPost(UpdatePostDto updatePostDto, Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "postTags", source = "postTagList")
    @Mapping(target = "postImages", source = "postImgList")
    @Mapping(target = "title", source = "createPostDto.title")
    @Mapping(target = "body", source = "createPostDto.body")
    @Mapping(target = "product", source = "product")
    @Mapping(target = "member", source = "member")
    Post createPostDtoToPost(CreatePostDto createPostDto, Product product,
        List<PostTag> postTagList, Member member, List<PostImg> postImgList);


    @Mapping(source = "post.id", target = "postId")
    @Mapping(source = "post.member.nickname", target = "nickname")
    @Mapping(source = "thumbNailUrl", target = "thumbNailImgUrl")
    @Mapping(source = "post.createdAt", target = "createdAt")
    FindPostResponse postToFindPostResponse(Post post, String thumbNailUrl);


    default List<FindPostResponse
        > postsToFindPostResponses(List<Post> posts, @Context String thumbNailUrl) {
        return posts.stream()
                    .map(post -> postToFindPostResponse(post, thumbNailUrl))
                    .collect(Collectors.toList());
    }


    @Mapping(source = "tagDto.body", target = "tagBody")
    PostTag updateThroughTagDto(TagDto tagDto, PostTag postTag);

    @Mapping(target = "likeThisPost", source ="likeThisPost")
    @Mapping(target = "likesCount", expression = "java(calculateLikesCount(post))")
    @Mapping(source = "post.member.nickname", target = "nickname")
    @Mapping(source = "post.member.profileImg", target = "profileImg")
    @Mapping(source = "tagDtoList", target = "tagList")
    @Mapping(source = "imgDtoList", target = "imgList")
    @Mapping(source = "productDto", target = "product")
    PostDetailResponse toPostDetailResponseDto(Post post, ProductDto productDto, List<TagDto> tagDtoList, List<ImgDto> imgDtoList,Boolean likeThisPost);

    default Long calculateLikesCount(Post post) {
        return post.getPostLikes().stream()
                .filter(PostLike::getIsLiked)
                .count();
    }

}


