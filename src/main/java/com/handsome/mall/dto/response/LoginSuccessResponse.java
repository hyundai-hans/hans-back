package com.handsome.mall.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginSuccessResponse {

  private String nickname;
  private String profileImg;
}
