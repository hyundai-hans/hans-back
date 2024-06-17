package com.handsome.mall.http.filter;

import com.handsome.mall.exception.AuthException;
import com.handsome.mall.handler.TokenHandler;
import com.handsome.mall.util.ExtractStringByRequest;
import com.handsome.mall.util.JsonBinderUtil;
import com.handsome.mall.valueobject.AuthRequestHeaderPrefix;
import com.handsome.mall.valueobject.JwtType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private TokenHandler tokenHandler;


  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String header = request.getHeader(AuthRequestHeaderPrefix.AUTHORIZATION_HEADER);

    if(header!=null){
    try {
      String token = ExtractStringByRequest.extractKey(request, AuthRequestHeaderPrefix.AUTHORIZATION_HEADER).substring(7);

      if (!tokenHandler.isValidToken(JwtType.access, token)) {
        response.setStatus(401);
      } else {
        setSecurityContext(tokenHandler.getUserId(JwtType.access, token));
        filterChain.doFilter(request, response);
      }
    } catch (ExpiredJwtException expiredJwtException) {
      JsonBinderUtil.setResponseWithJson(response, 417, expiredJwtException.getClaims());
      filterChain.doFilter(request,response);
    } catch (AuthException | MalformedJwtException | StringIndexOutOfBoundsException e) {
      response.setStatus(401);
    }
    }
    else filterChain.doFilter(request,response);

  }

  private void setSecurityContext(String subject) {
    SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(Long.parseLong(subject), null, null));
  }




}