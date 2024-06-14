package com.handsome.mall.member.repository;

import com.handsome.mall.member.entity.Member;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
  public Optional<Member> findByMemberByEmailAndPassword(String email, String password);

}
