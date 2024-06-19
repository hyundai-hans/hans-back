package com.handsome.mall.mapper;

import com.handsome.mall.dto.ImgDto;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostImg;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostImgMapper {
    PostImgMapper INSTANCE = Mappers.getMapper(PostImgMapper.class);
    @Mapping(target = "post", ignore = true)
    @Mapping(source = "imgUrl", target = "imgUrl")
    @Mapping(source = "isThumbnail", target = "isThumbnail")
    PostImg mapToPostImg(String imgUrl, boolean isThumbnail);

    @Named("mapImgUrlsToPostImages")
    default List<PostImg> mapImgUrlsToPostImages(List<String> imgUrls) {
        List<PostImg> postImages = new ArrayList<>();
        for (int i = 0; i < imgUrls.size(); i++) {
            String url = imgUrls.get(i);
            boolean isThumbnail = (i == 0);
            postImages.add(mapToPostImg(url, isThumbnail));
        }
        return postImages;
    }

    @Mapping(source = "imgDto.imgId",target = "id" )
     @Mapping(source = "imgDto.imgUrl", target = "imgUrl")
    @Mapping(source = "imgDto.isThumbnail", target = "isThumbnail", qualifiedByName = "toBoolean")
    PostImg imgDtoToPostImg(ImgDto imgDto,Post post);

    @Named("toBoolean")
    default boolean toBoolean(Boolean value) {
        return value != null && value;
    }

    @Mapping(source = "id", target = "imgId")
    ImgDto postImgToImgDto(PostImg postImg);
}
