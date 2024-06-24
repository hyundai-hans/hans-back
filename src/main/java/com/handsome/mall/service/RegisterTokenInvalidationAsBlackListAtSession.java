package com.handsome.mall.service;

import com.handsome.mall.valueobject.JwtType;
import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@AllArgsConstructor
@RequiredArgsConstructor
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
