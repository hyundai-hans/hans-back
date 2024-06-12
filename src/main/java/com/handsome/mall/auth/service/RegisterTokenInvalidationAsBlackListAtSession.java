package com.handsome.mall.auth.service;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterTokenInvalidationAsBlackListAtSession implements
    TokenInvalidationStrategy {

    private final HttpSession httpSession;


  @Override
  public void invalidate(String value) {

  }

  @Override
  public boolean isRegistered(String value) {
    return false;
  }
}
