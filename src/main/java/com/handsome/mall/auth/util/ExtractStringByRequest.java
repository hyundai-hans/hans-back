package com.handsome.mall.auth.util;

import javax.servlet.http.HttpServletRequest;

public class ExtractStringByRequest {


  private ExtractStringByRequest() {

  }

  public static String extractKey(HttpServletRequest request,String key) {
    String value = request.getHeader(key);
    if(value == null) throw new IllegalArgumentException("Request로 부터 존재하지 않는 value입니다.");
    return  value;
  }
}

