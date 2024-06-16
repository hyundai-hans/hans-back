package com.handsome.mall.config;

import com.handsome.mall.handler.SystemAuthenticationSuccessHandler;
import com.handsome.mall.handler.TokenHandler;
import com.handsome.mall.http.auth.UserAuthenticationManager;
import com.handsome.mall.repository.primary.MemberRepository;
import com.handsome.mall.service.JwtTokenProcessor;
import com.handsome.mall.http.filter.ExceptionHandlerFilter;
import com.handsome.mall.http.filter.JwtAuthenticationFilter;
import com.handsome.mall.http.filter.UserAuthenticationFilter;

import com.handsome.mall.service.RegisterTokenInvalidationAsBlackListAtSession;
import com.handsome.mall.service.TokenInvalidationStrategy;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
  private final MemberRepository memberRepository;
  @Value("${encrypt.key.access}")
  public String accessKey;


  @Bean
  public PasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

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
    http
        .authorizeRequests()
        .antMatchers("/**/login").permitAll().and()
        .addFilterBefore(userAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterAfter(jwtAuthenticationFilter(), userAuthenticationFilter().getClass())
        .addFilterAt(exceptionHandlerFilter(), ExceptionTranslationFilter.class);

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
  public AuthenticationManager userAuthenticationManager() {
    return new UserAuthenticationManager(memberRepository,bCryptPasswordEncoder());
  }

  @Bean
  public AuthenticationSuccessHandler successHandler() {
    return new SystemAuthenticationSuccessHandler(tokenHandler());
  }
}
