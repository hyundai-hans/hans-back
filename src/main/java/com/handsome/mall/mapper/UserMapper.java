package com.handsome.mall.mapper;

import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.entity.primary.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Member toMember(UserSignUpDto dto);

    UserSignUpDto toDto(Member member);

    LoginSuccessResponse toLoginSuccessDto(Member member);

    void updateMemberFromDto(UserUpdateDto userUpdateDto, @MappingTarget Member member);


}
