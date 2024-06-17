package com.handsome.mall.handler;

import com.handsome.mall.service.JwtTokenProcessor;
import com.handsome.mall.valueobject.JwtType;
import java.util.Map;
import lombok.AllArgsConstructor;


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

  public String getUserId(JwtType type, String value){
    return jwtTokenProcessor.getSubject(type,value);

  }



}
