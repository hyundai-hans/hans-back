package com.handsome.mall.mapper;

import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.response.UserInfoResponse;
import com.handsome.mall.entity.primary.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
   @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImg", constant = "default-img")
    @Mapping(target = "role", constant = "USER")
    Member toMember(UserSignUpDto dto);

    UserSignUpDto toDto(Member member);

    LoginSuccessResponse toLoginSuccessDto(Member member);

    void updateMemberFromDto(UserUpdateDto userUpdateDto, @MappingTarget Member member);

  @Mappings({
        @Mapping(source = "member.nickname", target = "nickname"),
        @Mapping(source = "member.email", target = "email"),
        @Mapping(source = "member.posts", target = "postList")
    })
    UserInfoResponse toUserInfoResponse(Member member);



}
