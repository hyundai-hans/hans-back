package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import org.springframework.stereotype.Service;

@Service
public interface PostService<UserId extends Object, PostId extends Number> {
  public void createPost(UserId userId, CreatePostDto createPostDto);

}
