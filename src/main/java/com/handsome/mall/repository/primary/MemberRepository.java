package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  public Optional<Member> findByEmailAndPassword(String email, String password);
  public Optional<Member> findById(Long id);
  public Optional<Member> findByEmail(String email);
  public Optional<Member> findByNickname(String nickname);
}
