package com.handsome.mall.service;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.repository.primary.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HansSnsPostService implements PostService<Long, Long> {

  private final PostRepository postRepository;

  @Override
  public void createPost(Long userId, CreatePostDto createPostDto) {

  }
}
