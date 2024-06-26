package com.handsome.mall.handler;


import com.handsome.mall.valueobject.AuthRequestHeaderPrefix;
import com.handsome.mall.valueobject.JwtType;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
public class SystemAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenHandler tokenHandler;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
  }

  /**
   * @TokenHandler.createToken(authentication.getName(),null) will not throw an NullPointException
   * Null check at TokenHandler
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    String token = tokenHandler.createToken(JwtType.access,authentication.getName(),null);
    response.setHeader(AuthRequestHeaderPrefix.AUTHORIZATION_HEADER,AuthRequestHeaderPrefix.TOKEN_PREFIX+token );


  }

}
