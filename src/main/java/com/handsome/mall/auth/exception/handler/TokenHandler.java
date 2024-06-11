package com.handsome.mall.auth.exception.handler;

import com.handsome.mall.auth.exception.helper.JwtTokenProcessor;
import com.handsome.mall.auth.service.InvalidateTokenStrategy;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TokenHandler {

  private final JwtTokenProcessor jwtTokenProcessor;
  private final InvalidateTokenStrategy<Object> invalidateAccessToken;


  public String createToken(String id,
      Map<String, Object> claimList) {
    return jwtTokenProcessor.createAccessToken(id, claimList);

  }

  public void invalidateToken(String key, String token) {
    invalidateAccessToken.invalidate(key, token);
  }


}
