package com.handsome.mall.mapper;

import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.response.UserInfoResponse;
import com.handsome.mall.entity.primary.Member;
import java.time.LocalDateTime;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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


    @Mapping(target = "nickname",source = "member.nickname")
    @Mapping(target = "profileImg",source = "member.profileImg")
    LoginSuccessResponse toLoginSuccessDto(Member member);


      @Mappings({
        @Mapping(source = "userUpdateDto.profileImg", target = "profileImg"),
        @Mapping(source = "userUpdateDto.nickname", target = "nickname"),
        @Mapping(source = "userUpdateDto.password", target = "password"),
              @Mapping(target = "createdAt", source = "member.createdAt")

    })
    Member mapUpdateDtoToMember(UserUpdateDto userUpdateDto, Member member);

  @Mappings({
        @Mapping(source = "member.nickname", target = "nickname"),
        @Mapping(source = "member.email", target = "email"),
        @Mapping(source = "member.posts", target = "postList")
    })
    UserInfoResponse toUserInfoResponse(Member member);



}
