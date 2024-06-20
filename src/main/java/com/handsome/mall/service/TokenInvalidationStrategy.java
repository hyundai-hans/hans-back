package com.handsome.mall.service;


public interface TokenInvalidationStrategy {
  void invalidate(String value);
  boolean isRegistered(String value);


}
