package com.handsome.mall.service;

import com.handsome.mall.exception.UserException;
import com.handsome.mall.repository.primary.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * This is the helper class for handling the user duplication check
 */
@RequiredArgsConstructor
@Service
public class UserDuplicationChecker {

  private final MemberRepository memberRepository;

  public void nickNameDuplicationChecker(String nickname){
    if(memberRepository.findByNickname(nickname).isPresent()) throw new UserException("이미 존재하는 닉네임입니다.");
  }

   public void emailDuplicationChecker(String email){
    if(memberRepository.findByEmail(email).isPresent()) throw new UserException("이미 존재하는 이메일입니다.");

  }

}
