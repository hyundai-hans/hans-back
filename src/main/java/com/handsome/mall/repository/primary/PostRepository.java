package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  public Optional<Post> findById(Long productId);
  public Optional<Post> findByMemberIdAndProductId(Long memberId, Long productId);

  @Query("SELECT p FROM Post p WHERE p.title LIKE :title%")
  List<Post> findByTitleLike(@Param("title") String title);
}
