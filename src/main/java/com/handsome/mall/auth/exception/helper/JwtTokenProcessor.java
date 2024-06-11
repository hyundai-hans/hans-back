package com.handsome.mall.auth.exception.helper;

import com.handsome.mall.auth.exception.AuthException;
import com.handsome.mall.auth.exception.util.JwtUtil;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class JwtTokenProcessor {

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


  public void validateAccessToken(String token) throws AuthException {
    JwtUtil.isTokenValid(token, accessKey);
  }


  private String createAccessTokenWithClaims(String subject, Map<String, Object> claimsList) {
    return JwtUtil.generateTokenWithClaims(subject, accessKey,
        accessKeyLifeTime, claimsList);
  }

  public String createAccessToken(String subject, Map<String, Object> claimsList) {

      return createAccessTokenWithClaims(subject, claimsList);
    }
  }


