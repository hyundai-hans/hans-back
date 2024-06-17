package com.handsome.mall.http.controller;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.http.message.SuccessResponse;
import com.handsome.mall.http.service.PostLikeService;
import com.handsome.mall.service.PostService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostRestController<UserId, PostId extends Long> {

  private final PostLikeService<UserId, PostId> postLikeService;
  private final PostService<UserId, PostId> postService;

  @GetMapping
  public ResponseEntity<SuccessResponse<List<FindPostResponse>>> searchPost(
      @RequestParam("title") String title) {
    List<FindPostResponse> findPostResponseList = postService.findPost(title);
    return ResponseEntity.ok(
        SuccessResponse.<List<FindPostResponse>>builder().message("타이틀 검색 완료")
            .status(HttpStatus.OK.toString())
            .data(findPostResponseList).build());
  }


  @PostMapping
  public ResponseEntity<SuccessResponse<Object>> createPost(
      @Valid @RequestBody CreatePostDto createPostDto, @AuthenticationPrincipal UserId userId) {
    postService.createPost(userId, createPostDto);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 작성 성공").status(HttpStatus.OK.toString()).build());
  }

  @PutMapping
  public ResponseEntity<SuccessResponse<Object>> updatePost(
      @Valid @RequestBody UpdatePostDto updatePostDto, @AuthenticationPrincipal UserId userId) {
    postService.updatePost(userId, updatePostDto);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 작성 성공").status(HttpStatus.OK.toString()).build());
  }


  @DeleteMapping("/{postId}")
  public ResponseEntity<SuccessResponse<Object>> deletePost(@PathVariable PostId postId,
      @AuthenticationPrincipal UserId userId) {
    postService.deletePost(userId, postId);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 작성 성공").status(HttpStatus.OK.toString()).build());
  }
  @PatchMapping("/{postId}")
  public ResponseEntity<SuccessResponse<Object>> likePost(@PathVariable PostId postId,
      @AuthenticationPrincipal UserId userId) {
    postLikeService.likesOrUnLikes(userId, postId);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 좋아요 혹은 취소 성공").status(HttpStatus.OK.toString()).build());
  }




}
