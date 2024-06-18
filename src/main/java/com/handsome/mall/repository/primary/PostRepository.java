package com.handsome.mall.repository.primary;

import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  public Optional<Post> findById(Long productId);

  public Optional<Post> findByMemberIdAndProductId(Long memberId, Long productId);

  @Query("SELECT p FROM Post p LEFT JOIN p.postLikes pl WHERE p.title LIKE %:title% GROUP BY p.id ORDER BY COUNT(pl.id) DESC")
  Page<Post> findByTitleContainingOrderByLikes(@Param("title") String title, Pageable pageable);

}
