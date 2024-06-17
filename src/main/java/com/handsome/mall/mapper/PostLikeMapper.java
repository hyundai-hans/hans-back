package com.handsome.mall.mapper;

import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostLike;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostLikeMapper {

    PostLikeMapper INSTANCE = Mappers.getMapper(PostLikeMapper.class);


    @Mapping(source = "updatedStatus", target = "isLiked")
    PostLike updatePostLike(PostLike postLike,boolean updatedStatus);
}
