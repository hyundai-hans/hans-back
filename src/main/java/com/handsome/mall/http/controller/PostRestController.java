package com.handsome.mall.http.controller;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.http.message.SuccessResponse;
import com.handsome.mall.service.PostService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostRestController<UserId, PostId extends Number> {

  private final PostService<UserId, PostId> postService;

  @PostMapping
  public ResponseEntity<SuccessResponse<Object>> createPost(
      @Valid @RequestBody CreatePostDto createPostDto, @AuthenticationPrincipal UserId id) {

  }


}
