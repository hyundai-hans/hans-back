package com.handsome.mall.service;

import org.springframework.stereotype.Service;


@Service
public interface PostLikeService<UserId, PostId> {

  public void likesOrUnLikes(UserId userId, PostId postId);

}
