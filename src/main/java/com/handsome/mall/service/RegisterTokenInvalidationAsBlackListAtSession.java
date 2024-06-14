package com.handsome.mall.service;

import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class RegisterTokenInvalidationAsBlackListAtSession implements
    TokenInvalidationStrategy {

    private  HttpSession httpSession;


  @Override
  public void invalidate(String value) {

  }

  @Override
  public boolean isRegistered(String value) {
    return false;
  }
}
