package com.handsome.mall.auth.http.filter;


import com.handsome.mall.auth.util.JsonBinderUtil;
import com.handsome.mall.exception.ErrorDTO;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (ExpiredJwtException e) {
      JsonBinderUtil.setResponseWithJson(response, 401,"로그인 시간이 만료되었습니다. 다시 로그인 해주세요.");
    }
    catch (RuntimeException e) {
      ErrorDTO dto = new ErrorDTO(String.valueOf(401), e.getMessage());
      JsonBinderUtil.setResponseWithJson(response, 401, dto);
    }
  }


  }

