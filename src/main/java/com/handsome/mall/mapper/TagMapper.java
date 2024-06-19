package com.handsome.mall.mapper;

import com.handsome.mall.dto.TagDto;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostTag;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TagMapper {

  TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

  @Mapping(target = "id", ignore=true)
  @Mapping(source = "post", target = "post")
  @Mapping(source = "tagDto.body", target = "tagBody")
  PostTag mapToPostTagFromDto(TagDto tagDto,Post post);

  TagDto toTagDTO(PostTag postTag);


  @Named("mapToPostTags")
  default List<PostTag> mapToPostTagsFromBody(List<String> tagBodies) {
    return tagBodies.stream()
        .map(this::mapToTagBody)
        .collect(Collectors.toList());
  }

  @Named("mapToTagBody")
  default PostTag mapToTagBody(String tagBody) {
    PostTag postTag = new PostTag();
    postTag.setTagBody(tagBody);
    return postTag;
  }

  @Mapping(source = "tagDto.body", target = "tagBody")
  PostTag updateThroughTagDto(TagDto tagDto, Post post);

  @Mapping(source = "tagBody", target = "body")
  TagDto postTagToTagDto(PostTag postTag);
}
