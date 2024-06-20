package com.handsome.mall.service;

import javax.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;


@AllArgsConstructor
public class RegisterTokenInvalidationAsBlackListAtSession implements
    TokenInvalidationStrategy {

    private  HttpSession httpSession;

  @Override
  public void invalidate(String value) {
    httpSession.setAttribute(value, System.currentTimeMillis());

  }

  @Override
  public boolean isRegistered(String value) {
    Object key = httpSession.getAttribute(value);
    return key != null;
  }
}
