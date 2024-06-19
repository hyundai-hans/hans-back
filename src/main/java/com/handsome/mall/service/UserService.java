package com.handsome.mall.service;

import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.response.UserInfoResponse;
import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  public void signUp(UserSignUpDto signUpDto);
  public LoginSuccessResponse getUserProfile(Long id);
  public void update(UserUpdateDto userUpdateDto, Long id);
  UserInfoResponse getInfo(Long userId);
}
