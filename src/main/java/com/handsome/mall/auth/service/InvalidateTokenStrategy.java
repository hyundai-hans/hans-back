package com.handsome.mall.auth.service;

import org.springframework.stereotype.Service;

@Service
public interface InvalidateTokenStrategy<T extends Object> {
  public void invalidate(T key, String value);
}
