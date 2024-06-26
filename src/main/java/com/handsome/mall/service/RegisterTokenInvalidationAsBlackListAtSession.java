package com.handsome.mall.service;

import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RegisterTokenInvalidationAsBlackListAtSession implements
    TokenInvalidationStrategy {

    private  final HttpSession httpSession;

  @Override
  public void invalidate(String value) {
    httpSession.setAttribute(value, System.currentTimeMillis());

  }

  @Override
  public boolean isRegistered( String value) {
    Object key = httpSession.getAttribute(value);
    return key != null;
  }
}
