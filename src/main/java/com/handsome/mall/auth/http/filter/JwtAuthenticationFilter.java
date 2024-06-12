package com.handsome.mall.auth.http.filter;

import com.handsome.mall.auth.exception.AuthException;
import com.handsome.mall.auth.handler.TokenHandler;
import com.handsome.mall.auth.helper.JwtTokenProcessor;
import com.handsome.mall.auth.util.ExtractStringByRequest;
import com.handsome.mall.auth.valueobject.JWTAuthenticationShouldNotFilterAntMatcher;
import com.handsome.mall.auth.valueobject.JwtType;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final TokenHandler tokenHandler;
  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
        return shouldNotFilterBySystemPolicy(request);
  }

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token =  ExtractStringByRequest.extractKey(request,"Authorization").substring(7);

    try {
      tokenHandler.isValidToken(JwtType.access,token);
    } catch (ExpiredJwtException expiredJwtException) {
      response.setStatus(401);
      throw new ExpiredJwtException(expiredJwtException.getHeader(), expiredJwtException.getClaims(), "Expired");
    } catch  (AuthException authException) {
      throw new AuthException(authException.getMessage());
    }

    filterChain.doFilter(request, response);
  }



  private boolean shouldNotFilterBySystemPolicy(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return requestURI.contains(JWTAuthenticationShouldNotFilterAntMatcher.SIGNUP_ANT)
        || requestURI.contains(JWTAuthenticationShouldNotFilterAntMatcher.LOGIN_ANT);
  }


}