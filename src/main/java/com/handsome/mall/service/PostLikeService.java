package com.handsome.mall.service;

import org.springframework.stereotype.Service;


@Service
public interface PostLikeService {
  public void likesOrUnLikes(Long userId, Long postId);

}
