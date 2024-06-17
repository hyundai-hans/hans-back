package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.UpdatePostDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PostService<UserId extends Object, PostId extends Number> {
  public void createPost(UserId userId, CreatePostDto createPostDto);
  void deletePost(UserId userId, PostId postId);
  List<FindPostResponse> findPostByTitle(String title);
  void updatePost(UserId userId, UpdatePostDto updatePostDto);
}
