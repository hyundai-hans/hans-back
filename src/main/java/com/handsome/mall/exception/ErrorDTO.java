package com.handsome.mall.exception;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class ErrorDTO {

  private final String code;
  private final String message;

}
