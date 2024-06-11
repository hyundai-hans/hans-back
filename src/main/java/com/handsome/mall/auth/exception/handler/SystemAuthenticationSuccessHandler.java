package com.handsome.mall.auth.exception.handler;


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
@Component
public class SystemAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final TokenHandler tokenHandler;


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authentication) throws IOException, ServletException {
    onAuthenticationSuccess(request, response, authentication);
    chain.doFilter(request,response);
  }

  /**
   * @TokenHandler.createToken(authentication.getName(),null) will not throw an NullPointException
   * Null check at TokenHandler
   */
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) {
    String token = tokenHandler.createToken(authentication.getName(),null);
    response.setHeader("Authorization","Bearer "+token );
  }

}
