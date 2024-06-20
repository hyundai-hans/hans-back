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

    List<Post> findByProductIdIn(List<Long> productIdList);

    Optional<Post> findById(Long productId);

      @Query("SELECT DISTINCT p FROM Post p JOIN p.postTags pt " +
           "WHERE (:title IS NULL OR p.title LIKE %:title%) " +
           "AND (:tagBody IS NULL OR pt.tagBody LIKE %:tagBody%)")
    Page<Post> findByTitleContainingAndTagBody(@Param("title") String title, @Param("tagBody") String tagBody, Pageable pageable);

}
