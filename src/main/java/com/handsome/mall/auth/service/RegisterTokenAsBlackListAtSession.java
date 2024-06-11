package com.handsome.mall.auth.service;

import javax.servlet.http.HttpServlet;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Service;

@Service
public class RegisterTokenAsBlackListAtSession implements
    InvalidateTokenStrategy {

  @Override
  public void invalidate(String key, String value) {


  }
}
