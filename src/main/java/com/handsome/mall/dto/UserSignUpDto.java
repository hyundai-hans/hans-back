package com.handsome.mall.dto;

import com.handsome.mall.annotation.NickName;
import com.handsome.mall.annotation.Password;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserSignUpDto {
  @NotNull
  @Email
  private String email;
  @NotNull
  @Password
  private String password;
  @NickName
  @NotNull
  private String nickname;
}
