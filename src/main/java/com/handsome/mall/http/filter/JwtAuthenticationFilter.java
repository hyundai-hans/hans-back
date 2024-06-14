package com.handsome.mall.http.filter;

import com.handsome.mall.exception.AuthException;
import com.handsome.mall.handler.TokenHandler;
import com.handsome.mall.util.ExtractStringByRequest;
import com.handsome.mall.valueobject.JWTAuthenticationShouldNotFilter;
import com.handsome.mall.valueobject.JwtType;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private TokenHandler tokenHandler;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    return shouldNotFilterBySystemPolicy(request)  || shouldNotFilterForAllUserCanAccess(request);
  }

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String token =  ExtractStringByRequest.extractKey(request,"Authorization").substring(7);

    try {
      tokenHandler.isValidToken(JwtType.access,token);
    } catch (ExpiredJwtException expiredJwtException) {
      response.setStatus(401);
      throw new ExpiredJwtException(expiredJwtException.getHeader(),
          expiredJwtException.getClaims(), "로그인 시간이 만료되었습니다.");
    } catch (AuthException authException) {
      throw new AuthException(authException.getMessage());
    }

    filterChain.doFilter(request, response);
  }




  private boolean shouldNotFilterForAllUserCanAccess(HttpServletRequest request) {
    String uri = request.getRequestURI();
    if (uri.matches(JWTAuthenticationShouldNotFilter.POST_SEE_REGEX) && request.getMethod().equals(HttpMethod.GET.name())) {
      return true;
    }
    if (uri.contains(JWTAuthenticationShouldNotFilter.POST_SEARCH)) {
      return true;
    }
    if(uri.contains(JWTAuthenticationShouldNotFilter.TAG_SEARCH)) {
      return true;
    }
    return false;
  }




  private boolean shouldNotFilterBySystemPolicy(HttpServletRequest request) {
    String requestURI = request.getRequestURI();
    return requestURI.contains(JWTAuthenticationShouldNotFilter.SIGNUP_ANT)
        || requestURI.contains(JWTAuthenticationShouldNotFilter.LOGIN_ANT);
  }


}