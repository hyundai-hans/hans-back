package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  Optional<Member> findByEmailAndPassword(String email, String password);
  Optional<Member> findById(Long id);
  Optional<Member> findByEmail(String email);
  Optional<Member> findByNickname(String nickname);
}
