package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
  public void createPost(Long userId, CreatePostDto createPostDto);
  void deletePost(Long userId, Long postId);
  void updatePost(Long userId, UpdatePostDto updatePostDto);
  List<FindPostResponse> findPostByTitle(String title, String tagName, Pageable pageable);
  PostDetailResponse findPostById(Long postId);
}
