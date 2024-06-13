package com.handsome.mall.auth.http.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handsome.mall.auth.exception.AuthException;
import com.handsome.mall.auth.handler.SystemAuthenticationSuccessHandler;
import com.handsome.mall.member.dto.UserLoginDto;
import com.handsome.mall.member.valueobject.Role;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private SystemAuthenticationSuccessHandler successHandler;
  private AuthenticationManager authenticationManager;

  public UserAuthenticationFilter(
      SystemAuthenticationSuccessHandler successHandler,
      AuthenticationManager authenticationManager) {
    super(authenticationManager);
    this.successHandler = successHandler;
    this.authenticationManager = authenticationManager;
  }

  private UserLoginDto getLoginDtoFromRequest(HttpServletRequest request)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(request.getInputStream(),
        UserLoginDto.class);
  }


  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException {
    try {
      UserLoginDto dto = getLoginDtoFromRequest(request);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(),
              Collections.singleton(new SimpleGrantedAuthority(Role.USER.name()))));
    } catch (BadCredentialsException | IOException | AuthException e) {
      response.setStatus(401);
      throw new BadCredentialsException("일치하지 않은 이메일과 패스워드입니다.");
    }

  }

  @Override
  public void successfulAuthentication(HttpServletRequest request, HttpServletResponse
      response, FilterChain
      chain,
      Authentication authResult) throws IOException, ServletException {
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authResult);
    SecurityContextHolder.setContext(context);
    successHandler.onAuthenticationSuccess(request, response, chain, authResult);
  }


}