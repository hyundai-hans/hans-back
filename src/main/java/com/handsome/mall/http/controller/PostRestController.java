package com.handsome.mall.http.controller;

import com.handsome.mall.dto.CreatePostDto;
import com.handsome.mall.dto.FindPostResponse;
import com.handsome.mall.dto.UpdatePostDto;
import com.handsome.mall.dto.response.PostDetailResponse;
import com.handsome.mall.http.message.SuccessResponse;
import com.handsome.mall.service.PostLikeService;
import com.handsome.mall.service.PostService;
import com.handsome.mall.util.PaginationUtil;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
@Slf4j
public class PostRestController<UserId, PostId extends Long> {

  private final PostLikeService<UserId, PostId> postLikeService;
  private final PostService<UserId, PostId> postService;


  @GetMapping
  public ResponseEntity<SuccessResponse<List<FindPostResponse>>> findPosts(
      @RequestParam(required = false) String title,
      @RequestParam(required = false) String tag,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "created_at") String sort,
      @RequestParam(defaultValue = "desc") String by
  ) {

    Pageable pageable = PaginationUtil.createPageRequest(page, size, sort, by);

    List<FindPostResponse> findPostResponseList = postService.findPostByTitle(title,tag, pageable);

    return ResponseEntity.ok(
        SuccessResponse.<List<FindPostResponse>>builder().message("포스트 반환 성공").status(HttpStatus.OK.toString())
            .data(findPostResponseList).build());
  }

  @PostMapping
  public ResponseEntity<SuccessResponse<Object>> createPost(
      @Valid @RequestBody CreatePostDto createPostDto, @AuthenticationPrincipal UserId userId) {
    postService.createPost(userId, createPostDto);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 작성 성공").status(HttpStatus.OK.toString()).build());
  }

    @GetMapping("/{postId}")
  public ResponseEntity<SuccessResponse<PostDetailResponse>> findPost(@PathVariable PostId postId) {
    PostDetailResponse postDetailResponse = postService.findPostById(postId);
      return ResponseEntity.ok(
        SuccessResponse.<PostDetailResponse>builder().message("게시글 조회 성공").data(postDetailResponse).status(HttpStatus.OK.toString()).build());
  }


  @PutMapping
  public ResponseEntity<SuccessResponse<Object>> updatePost(
      @Valid @RequestBody UpdatePostDto updatePostDto, @AuthenticationPrincipal UserId userId) {
    postService.updatePost(userId, updatePostDto);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 업데이트 성공").status(HttpStatus.OK.toString()).build());
  }


  @DeleteMapping("/{postId}")
  public ResponseEntity<SuccessResponse<Object>> deletePost(@PathVariable PostId postId,
      @AuthenticationPrincipal UserId userId) {
    postService.deletePost(userId, postId);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 삭제 성공").status(HttpStatus.OK.toString()).build());
  }
  @PatchMapping("/{postId}")
  public ResponseEntity<SuccessResponse<Object>> likePost(@PathVariable PostId postId,
      @AuthenticationPrincipal UserId userId) {
    postLikeService.likesOrUnLikes(userId, postId);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("게시글 좋아요 혹은 취소 성공").status(HttpStatus.OK.toString()).build());
  }




}
