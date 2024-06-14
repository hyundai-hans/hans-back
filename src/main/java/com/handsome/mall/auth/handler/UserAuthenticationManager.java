package com.handsome.mall.auth.handler;

import com.handsome.mall.auth.exception.AuthException;
import com.handsome.mall.member.entity.Member;
import com.handsome.mall.member.repository.UserRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationManager {

  private UserRepository userRepository;

  public UserAuthenticationManager(UserRepository userRepository) {
    this.userRepository = userRepository;
  }



  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getPrincipal().toString();
    String password = authentication.getCredentials().toString();

    Member member = userRepository.findByMemberByEmailAndPassword(email, password)
        .orElseThrow(() -> {
          throw new AuthException("존재하지 않은 이메일과 패스워드입니다.");
        });
    return new UsernamePasswordAuthenticationToken(member.getEmail(),member.getPassword(),
        Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
  }
}
