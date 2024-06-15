package com.handsome.mall.mapper;

import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.response.UserInfoResponse;
import com.handsome.mall.entity.primary.Member;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profileImg", constant = "default-img")
    @Mapping(target = "role", constant = "ROLE_USER")
    @Mapping(target = "password", source = "passwordEncoder")
    Member toMember(UserSignUpDto dto,  String passwordEncoder);

    default String encodePassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.encode(password);
    }

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
