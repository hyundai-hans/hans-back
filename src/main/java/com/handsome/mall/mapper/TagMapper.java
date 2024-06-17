package com.handsome.mall.mapper;

import com.handsome.mall.dto.TagDto;
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

  @Mapping(target = "post", ignore = true)
  @Mapping(source = "tagBody", target = "tagBody")
  PostTag mapToPostTag(String tagBody);

  @Named("mapToPostTags")
  default List<PostTag> mapToPostTags(List<String> tagBodies) {
    return tagBodies.stream()
        .map(this::mapToPostTag)
        .collect(Collectors.toList());
  }


  @Mapping(source = "tagId", target = "id")
  @Mapping(source = "body", target = "tagBody")
  PostTag tagDtoToPostTag(TagDto tagDto);

  @Mapping(source = "id", target = "tagId")
  @Mapping(source = "tagBody", target = "body")
  TagDto postTagToTagDto(PostTag postTag);
}
