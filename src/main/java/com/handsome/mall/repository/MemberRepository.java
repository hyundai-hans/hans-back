package com.handsome.mall.repository;

import com.handsome.mall.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
  public Optional<Member> findByEmailAndPassword(String email, String password);
}
