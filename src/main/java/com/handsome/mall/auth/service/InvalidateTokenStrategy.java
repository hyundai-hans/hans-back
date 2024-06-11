package com.handsome.mall.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface InvalidateTokenStrategy {
  public void invalidate(String key, String value);
}
