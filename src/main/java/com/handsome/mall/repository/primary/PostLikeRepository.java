package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.entity.primary.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
  Optional<PostLike> findByMemberAndPost(Member member, Post post);
}
