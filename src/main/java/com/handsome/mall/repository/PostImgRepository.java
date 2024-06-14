package com.handsome.mall.repository;

import com.handsome.mall.entity.PostImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImgRepository extends JpaRepository<PostImg, Long> {
}
