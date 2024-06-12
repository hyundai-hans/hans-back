package com.handsome.mall.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface TokenInvalidationStrategy {
  public void invalidate( String value);
  public boolean isRegistered(String value);


}
