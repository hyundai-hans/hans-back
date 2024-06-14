package com.handsome.mall.auth.handler;

import com.handsome.mall.auth.helper.JwtTokenProcessor;
import com.handsome.mall.auth.valueobject.JwtType;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@AllArgsConstructor
public class TokenHandler {

  private JwtTokenProcessor jwtTokenProcessor;


  public String createToken(String id,
      Map<String, Object> claimList) {
    return jwtTokenProcessor.createAccessToken(id, claimList);

  }
  public void invalidateToken(JwtType type, String value){
     jwtTokenProcessor.invalidateToken(type, value);

  }

  public boolean isValidToken(JwtType type, String value){
    return jwtTokenProcessor.isValidToken(type, value);
  }


}
