package com.handsome.mall.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserSystemLoginDto {

  @NotNull
  private String email;
  @NotNull
  private String password;
}
