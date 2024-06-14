package com.handsome.mall.auth.service;

import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
