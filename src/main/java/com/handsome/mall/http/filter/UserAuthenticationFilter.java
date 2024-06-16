package com.handsome.mall.http.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handsome.mall.dto.UserSystemLoginDto;
import com.handsome.mall.exception.AuthException;
import com.handsome.mall.valueobject.Role;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationSuccessHandler successHandler;
  private AuthenticationManager authenticationManager;

  public UserAuthenticationFilter(
      AuthenticationSuccessHandler successHandler,
      AuthenticationManager authenticationManager) {
    super(authenticationManager);
    this.successHandler = successHandler;
    this.authenticationManager = authenticationManager;
  }

  private UserSystemLoginDto getLoginDtoFromRequest(HttpServletRequest request)
      throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(request.getInputStream(),
        UserSystemLoginDto.class);
  }


  @SneakyThrows
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response)
      throws AuthenticationException {
    try {
      UserSystemLoginDto dto = getLoginDtoFromRequest(request);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword(),
              Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.name()))));

    } catch (BadCredentialsException | AuthException e) {
      response.setStatus(401);
      throw new BadCredentialsException("일치하지 않은 이메일과 패스워드입니다.");
    } catch (IOException ioException) {
      response.setStatus(500);
      throw new IOException("Stream is closed");
    }

  }

  @Override
  public void successfulAuthentication(HttpServletRequest request, HttpServletResponse
      response, FilterChain
      chain,
      Authentication authResult) throws ServletException, IOException {
    SecurityContextHolder.getContext().setAuthentication(authResult);
    successHandler.onAuthenticationSuccess(request, response, authResult);
  }

}