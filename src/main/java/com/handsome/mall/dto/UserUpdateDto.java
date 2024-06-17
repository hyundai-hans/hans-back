package com.handsome.mall.dto;

import com.handsome.mall.annotation.Password;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserUpdateDto {

  @NotNull
  private String profileImg;
  @NotNull
  private String nickname;
  @Password
  @NotNull
  private String password;

}
