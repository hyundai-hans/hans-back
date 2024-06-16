package com.handsome.mall.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserUpdateDto {

  @NotNull
  private String profileImg;
  @NotNull
  private String nickname;
  @NotNull
  private String password;

}
