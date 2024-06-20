package com.handsome.mall.mapper;

import com.handsome.mall.dto.HistoryPostPersistDto;
import com.handsome.mall.dto.response.PostHistoryResponse;
import com.handsome.mall.entity.history.ViewHistory;
import com.handsome.mall.entity.id.ViewHistoryId;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import com.handsome.mall.exception.HistoryException;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HistoryMapper {

    HistoryMapper INSTANCE = Mappers.getMapper(HistoryMapper.class);

    @Mapping(source = "dto.memberId", target = "id.memberId")
    @Mapping(source = "dto.postId", target = "id.postId")
    ViewHistory toEntity(HistoryPostPersistDto dto, Long readCount);

    HistoryPostPersistDto toDto(ViewHistory viewHistory);


    @Mapping(source = "id", target = "postId")
    @Mapping(source = "member.nickname", target = "nickname")
    @Mapping(source = "postImages", target = "thumbnailImgUrl", qualifiedByName = "mapThumbnailImageUrl")
    PostHistoryResponse toPostHistoryResponse(Post post);

    @Named("mapThumbnailImageUrl")
    default String mapThumbnailImageUrl(List<PostImg> postImages) {
        return postImages.stream().filter(PostImg::getIsThumbnail).findFirst()
            .orElseThrow(() -> new HistoryException("히스토리에 존재하는 이미지가 썸네일이 존재하지 않습니다."))
            .getImgUrl();
    }
}
