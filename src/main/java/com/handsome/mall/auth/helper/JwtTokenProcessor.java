package com.handsome.mall.auth.helper;

import com.handsome.mall.auth.exception.AuthException;
import com.handsome.mall.auth.service.TokenInvalidationStrategy;
import com.handsome.mall.auth.util.JwtUtil;
import com.handsome.mall.auth.valueobject.JwtType;
import java.util.Map;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtTokenProcessor {


  private final TokenInvalidationStrategy tokenInvalidationStrategy;
  public static String accessKey;
  public static long accessKeyLifeTime;


  @Value("${encrypt.key.access}")
  private void setAccessKey(String accessKey) {
    JwtTokenProcessor.accessKey = accessKey;
  }

  @Value("${encrypt.key.life-time}")
  private void setAccessKeyLifeTime(long accessKeyLifeTime) {
    JwtTokenProcessor.accessKeyLifeTime = accessKeyLifeTime;
  }

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
    return tokenInvalidationStrategy.isRegistered(token) && JwtUtil.isTokenValid(token, accessKey);
  }

  public String createAccessToken(String subject, Map<String, Object> claimsList) {

    if (claimsList == null) {
      return JwtUtil.generateToken(subject, accessKey, accessKeyLifeTime);
    }
    return JwtUtil.generateTokenWithClaims(subject, accessKey,
        accessKeyLifeTime, claimsList);
  }
  }


