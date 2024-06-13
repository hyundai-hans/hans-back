package com.handsome.mall.auth.config;

import com.handsome.mall.auth.handler.TokenHandler;
import com.handsome.mall.auth.handler.UserAuthenticationManager;
import com.handsome.mall.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private final UserRepository userRepository;


  @Bean
  public UsernamePasswordAuthenticationFilter storeManagerAuthenticationFilter() {
    StoreManagerAuthenticationFilter authenticationFilter = new StoreManagerAuthenticationFilter(
        authenticationSuccessHandler, storeAuthenticationManager());
    authenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    authenticationFilter.setFilterProcessesUrl("/**/stores/login");
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
}
