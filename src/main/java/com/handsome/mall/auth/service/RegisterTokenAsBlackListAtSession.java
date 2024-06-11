package com.handsome.mall.auth.service;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public class RegisterTokenAsBlackListAtSession implements
    InvalidateTokenStrategy<HttpSession> {

  @Override
  public void invalidate(HttpSession key, String value) {
        key.setAttribute(value,key.getId());
  }
}
