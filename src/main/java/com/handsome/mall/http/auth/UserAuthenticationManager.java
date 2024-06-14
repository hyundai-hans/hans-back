package com.handsome.mall.http.auth;

import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.exception.AuthException;
import com.handsome.mall.repository.primary.MemberRepository;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


@RequiredArgsConstructor
public class UserAuthenticationManager implements AuthenticationManager {

  private MemberRepository memberRepository;

  public UserAuthenticationManager(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }



  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String email = authentication.getPrincipal().toString();
    String password = authentication.getCredentials().toString();

    Member member = memberRepository.findByEmailAndPassword(email, password)
        .orElseThrow(() -> {
          throw new AuthException("존재하지 않은 이메일과 패스워드입니다.");
        });
    return new UsernamePasswordAuthenticationToken(member.getEmail(),member.getPassword(),
        Collections.singleton(new SimpleGrantedAuthority(member.getRole())));
  }
}
