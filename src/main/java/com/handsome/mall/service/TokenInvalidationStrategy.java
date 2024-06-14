package com.handsome.mall.service;


public interface TokenInvalidationStrategy {
  public void invalidate( String value);
  public boolean isRegistered(String value);


}
