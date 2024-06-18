package com.handsome.mall.mapper;

import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.response.UserInfoResponse;
import com.handsome.mall.entity.primary.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", source = "passwordEncoder")
    @Mapping(target = "role", constant= "ROLE_USER")
    @Mapping(target = "profileImg", constant = "blob:https://i.namu.wiki/i/iC-OoyHtubjYG7-E96z5YcsYWUw4ADxa-Cg_u5e-A4Dd2uiKsSPiiz-_ijxa0lK4b4mxhnkob8Gn_4J6nOdq1WwYtxFHdxMaAlp148rf3Kvpi1IUNgV2gnvraqk-XVKKBCWBSEDgKP7t5-gtfQQu-g.webp")
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
        @Mapping(source = "userUpdateDto.password", target = "password")

    })
    Member mapUpdateDtoToMember(UserUpdateDto userUpdateDto, Member member);

  @Mappings({
        @Mapping(source = "member.nickname", target = "nickname"),
        @Mapping(source = "member.email", target = "email"),
        @Mapping(source = "member.posts", target = "postList")
    })
    UserInfoResponse toUserInfoResponse(Member member);



}
