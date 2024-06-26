package com.handsome.mall.handler;

import com.handsome.mall.exception.AuthException;
import com.handsome.mall.service.JwtAccessTokenProcessor;
import com.handsome.mall.valueobject.JwtType;
import java.util.Map;
import lombok.AllArgsConstructor;


/**
 * This is the TokenHandler which is adapted the Facade pattern. when Token type is created
 * There should be another TokenProcessor
 * @see JwtType
 */

@AllArgsConstructor
public class TokenHandler {

  private JwtAccessTokenProcessor jwtAccessTokenProcessor;

  public String createToken(JwtType jwtType, String id,
      Map<String, Object> claimList) {
    if (jwtType.equals(JwtType.access)) {
      return jwtAccessTokenProcessor.createAccessToken(id, claimList);
    }
    throw new AuthException("존재하지 않은 토큰 타입입니다.");
  }

  public void invalidateToken(JwtType jwtType, String value) {
    if (jwtType.equals(JwtType.access)) {
      jwtAccessTokenProcessor.invalidateToken(value);
    }
    throw new AuthException("존재하지 않은 토큰 타입입니다.");
  }

  public boolean isValidToken(JwtType jwtType, String value) {
    if (jwtType.equals(JwtType.access)) {
      return jwtAccessTokenProcessor.isValidToken(jwtType, value);
    }
    throw new AuthException("존재하지 않은 토큰 타입입니다.");
  }

  public String getUserId(JwtType jwtType, String value) {
    if (jwtType.equals(JwtType.access)) {
      return jwtAccessTokenProcessor.getSubject(jwtType, value);
    }
    throw new AuthException("존재하지 않은 토큰 타입입니다.");
  }

}
