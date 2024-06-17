package com.handsome.mall.service;

import com.handsome.mall.exception.AuthException;
import com.handsome.mall.util.JwtUtil;
import com.handsome.mall.valueobject.JwtType;
import java.util.Map;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class JwtTokenProcessor {

  public String accessKey;
  public long accessKeyLifeTime;
  private TokenInvalidationStrategy tokenInvalidationStrategy;


  public boolean isValidToken(JwtType keyType, String token) {
    if (keyType.equals(JwtType.access)) {
      return validateAccessToken(token);
    }
    throw new AuthException("존재하지 않는 JWT 토큰 타입입니다.");
  }


  public void invalidateToken(JwtType type, String token) {
    if(type.equals(JwtType.access)) tokenInvalidationStrategy.invalidate(token);
    throw new AuthException("존재하지 않는 JWT 토큰 타입입니다.");
  }

  private boolean validateAccessToken(String token) throws AuthException {
    return !tokenInvalidationStrategy.isRegistered(token) && JwtUtil.isTokenValid(token, accessKey);
  }

  public String createAccessToken(String subject, Map<String, Object> claimsList) {

    if (claimsList == null) {
      return JwtUtil.generateToken(subject, accessKey, accessKeyLifeTime);
    }
    return JwtUtil.generateTokenWithClaims(subject, accessKey,
        accessKeyLifeTime, claimsList);
  }

  public String getSubject(JwtType jwtType,String token){
    if(jwtType.equals(JwtType.access)){
    return JwtUtil.extractTokenSubject(token,accessKey);
    }
    throw new AuthException("존재하지 않는 토큰 타입입니다.");
  }
  }


