package com.handsome.mall.service;


import com.handsome.mall.valueobject.JwtType;

public interface TokenInvalidationStrategy {
  void invalidate(String value);
  boolean isRegistered(String value);


}
