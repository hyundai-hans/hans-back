package com.handsome.mall.http.controller;

import com.handsome.mall.dto.EmailDto;
import com.handsome.mall.dto.NicknameDto;
import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.http.message.SuccessResponse;
import com.handsome.mall.service.TokenInvalidationStrategy;
import com.handsome.mall.service.UserDuplicationChecker;
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

  private final TokenInvalidationStrategy strategy;
  private final UserService<ID> userService;
  private final UserDuplicationChecker duplicationChecker;

  @PostMapping("/email")
  public ResponseEntity<SuccessResponse<Object>> emailDuplicationCheck(
      @RequestBody @Valid EmailDto emailDto) {
    duplicationChecker.emailDuplicationChecker(emailDto.getEmail());
    return ResponseEntity.ok(
        SuccessResponse.builder().status(HttpStatus.OK.toString()).message("중복 되지 않은 이메일입니다.").build());
  }

  @PostMapping("/nickname")
  public ResponseEntity<SuccessResponse<Object>> nicknameDuplicatonChek(@RequestBody @Valid
      NicknameDto nicknameDto) {
    duplicationChecker.nickNameDuplicationChecker(nicknameDto.getNickname());
   return ResponseEntity.ok(
        SuccessResponse.builder().status(HttpStatus.OK.toString()).message("중복되지 않은 닉네임입니다.").build());

  }


  @PostMapping
  public ResponseEntity<SuccessResponse<Object>> signUp(
      @RequestBody @Valid UserSignUpDto signUpDto) {
    userService.signUp(signUpDto);
    return ResponseEntity.ok(
        SuccessResponse.builder().message("사용자 회원가입 완료").status(HttpStatus.OK.toString()).build());
  }

  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("로그인 성공");
  }

  @GetMapping("/profile")
  public ResponseEntity<SuccessResponse<LoginSuccessResponse>> getUserProfile(@AuthenticationPrincipal ID id) {
    LoginSuccessResponse response = userService.getUserProfile(id);
    return ResponseEntity.ok(SuccessResponse.<LoginSuccessResponse>builder().data(response).message("프로필 이미지 전달 성공").status(HttpStatus.OK.toString()).build());
  }

  @PostMapping("/logout")
  public ResponseEntity<SuccessResponse<Object>> logout(HttpServletRequest request) {
    String token = request.getHeader(AuthRequestHeaderPrefix.AUTHORIZATION_HEADER)
        .substring(AuthRequestHeaderPrefix.TOKEN_PREFIX.length());
    strategy.invalidate(token);
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

    return ResponseEntity.ok(
        SuccessResponse.builder().status(HttpStatus.OK.toString()).message("유저 정보 반환")
            .data(userService.getInfo(userId)).build());
  }




}


