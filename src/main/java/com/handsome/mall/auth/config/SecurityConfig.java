package com.handsome.mall.auth.config;

import com.handsome.mall.auth.handler.SystemAuthenticationSuccessHandler;
import com.handsome.mall.auth.handler.TokenHandler;
import com.handsome.mall.auth.handler.UserAuthenticationManager;
import com.handsome.mall.auth.helper.JwtTokenProcessor;
import com.handsome.mall.auth.http.filter.ExceptionHandlerFilter;
import com.handsome.mall.auth.http.filter.JwtAuthenticationFilter;
import com.handsome.mall.auth.http.filter.UserAuthenticationFilter;
import com.handsome.mall.auth.service.RegisterTokenInvalidationAsBlackListAtSession;
import com.handsome.mall.auth.service.TokenInvalidationStrategy;
import com.handsome.mall.member.repository.UserRepository;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final HttpSession httpSession;
  @Value("${encrypt.key.life-time}")
  public long accessKeyLifeTime;
  private final UserRepository userRepository;
  @Value("${encrypt.key.access}")
  public String accessKey;

  @Bean
  public ExceptionHandlerFilter exceptionHandlerFilter() {
    return new ExceptionHandlerFilter();

  }


  @Bean
  public TokenInvalidationStrategy tokenInvalidationStrategy() {
    return new RegisterTokenInvalidationAsBlackListAtSession(httpSession);
  }

  @Bean
  public JwtTokenProcessor jwtTokenProcessor() {
    return new JwtTokenProcessor(accessKey, accessKeyLifeTime, tokenInvalidationStrategy());
  }

  @Bean
  public TokenHandler tokenHandler() {
    return new TokenHandler(jwtTokenProcessor());
  }


  @Bean
  public SecurityFilterChain storeSecurityFilterChain(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.
        addFilterAt(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).
        addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class).
        addFilterAt(exceptionHandlerFilter(), ExceptionTranslationFilter.class);

    return http.build();
  }

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(tokenHandler());
  }

  @Bean
  public UsernamePasswordAuthenticationFilter userAuthenticationFilter() {
    UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter(
        successHandler(), userAuthenticationManager());
    authenticationFilter.setAuthenticationSuccessHandler(successHandler());
    authenticationFilter.setFilterProcessesUrl("/**/login");
    return authenticationFilter;
  }


  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public AuthenticationManager userAuthenticationManager() {
    return new UserAuthenticationManager(userRepository);
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new SystemAuthenticationSuccessHandler(tokenHandler());
  }
}
