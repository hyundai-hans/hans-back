package com.handsome.mall.repository;

import com.handsome.mall.entity.PostTag;
import com.handsome.mall.entity.PostTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTagRepository extends JpaRepository<PostTag, Long> {
}
