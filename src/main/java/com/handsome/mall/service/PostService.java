package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService<UserId extends Object, PostId extends Number> {
  public void createPost(UserId userId, CreatePostDto createPostDto);
  void deletePost(UserId userId, PostId postId);
  void updatePost(UserId userId, UpdatePostDto updatePostDto);
  List<FindPostResponse> findPostByTitle(String title, String tagName, Pageable pageable);
  PostDetailResponse findPostById(PostId postId);
}
