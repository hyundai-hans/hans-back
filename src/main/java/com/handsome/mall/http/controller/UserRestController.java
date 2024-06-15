package com.handsome.mall.http.controller;

import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.http.message.SuccessResponse;
import com.handsome.mall.service.UserService;
import com.handsome.mall.valueobject.AuthRequestHeaderPrefix;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping(value = "/users")
@RestController
public class UserRestController<ID> {

  private final UserService<ID> userService;

  @PostMapping
  public ResponseEntity<SuccessResponse<Object>> signUp(
      @RequestBody @Valid UserSignUpDto signUpDto) {
    userService.signUp(signUpDto);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("사용자 회원가입 완료").status(HttpStatus.OK.toString()).build());
  }

  @PostMapping("/login")
  public ResponseEntity<SuccessResponse<LoginSuccessResponse>> login(
      @AuthenticationPrincipal ID userId) {
    LoginSuccessResponse loginSuccessDto = userService.login(userId);
    return ResponseEntity.ok(SuccessResponse.<LoginSuccessResponse>builder().data(loginSuccessDto)
        .status(HttpStatus.OK.toString())
        .message("로그인 성공").build());
  }

  @PostMapping("/logout")
  public ResponseEntity<SuccessResponse<Object>> logout(@AuthenticationPrincipal ID userId,
      HttpSession httpSession, HttpServletRequest request) {
    String token = request.getHeader(AuthRequestHeaderPrefix.AUTHORIZATION_HEADER)
        .substring(AuthRequestHeaderPrefix.TOKEN_PREFIX.length());
    httpSession.setAttribute(token, userId);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("로그아웃 성공").status(HttpStatus.OK.toString()).build());
  }


  @PutMapping
  public ResponseEntity<SuccessResponse<Object>> update(@AuthenticationPrincipal ID userId,
      @Valid @RequestBody UserUpdateDto userUpdateDto) {
    userService.update(userUpdateDto, userId);
   return ResponseEntity.ok(
        SuccessResponse.builder().status(HttpStatus.OK.toString()).message("유저 업데이트 성공").build());
  }

  @GetMapping
  public ResponseEntity<SuccessResponse<Object>> getUserInfo(@AuthenticationPrincipal ID userId) {
    userService.getInfo(userId);
   return ResponseEntity.ok(
        SuccessResponse.builder().status(HttpStatus.OK.toString()).message("유저 업데이트 성공").build());
  }



}


