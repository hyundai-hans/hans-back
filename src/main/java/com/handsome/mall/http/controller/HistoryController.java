package com.handsome.mall.http.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.handsome.mall.dto.response.PostHistoryResponse;
import com.handsome.mall.handler.PostHistoryManageCommandHandler;
import com.handsome.mall.http.message.SuccessResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users/histories")
@RestController
public class HistoryController {

  private final PostHistoryManageCommandHandler handler;

  @GetMapping("/products")
  public ResponseEntity<SuccessResponse<List<PostHistoryResponse>>> handleHistory(
      @AuthenticationPrincipal Long userId)
      throws JsonProcessingException {
    List<PostHistoryResponse> response = handler.handle(userId);
    return ResponseEntity.ok(SuccessResponse.<List<PostHistoryResponse>>builder()
        .data(response).message("상품 히스토리 반환 성공").status(
            HttpStatus.OK.toString()).build());
  }


}
