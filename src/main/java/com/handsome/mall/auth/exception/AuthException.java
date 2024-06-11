package com.handsome.mall.auth.exception;

public class
AuthException extends RuntimeException{

  public AuthException(String message) {
    super(message);
  }

  public AuthException(String message, Throwable cause) {
    super(message, cause);
  }
}
