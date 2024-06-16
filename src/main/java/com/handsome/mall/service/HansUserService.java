package com.handsome.mall.service;

import com.handsome.mall.dto.PostDto;
import com.handsome.mall.dto.UserSignUpDto;
import com.handsome.mall.dto.UserUpdateDto;
import com.handsome.mall.dto.response.LoginSuccessResponse;
import com.handsome.mall.dto.response.UserInfoResponse;
import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.exception.UserException;
import com.handsome.mall.mapper.PostMapper;
import com.handsome.mall.mapper.UserMapper;
import com.handsome.mall.repository.primary.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class HansUserService implements UserService<Long> {

  private final MemberRepository memberRepository;
  private final UserDuplicationChecker userDuplicationChecker;
  private final PasswordEncoder encoder;

  @Override
  public void signUp(UserSignUpDto signUpDto) {
    String encodedPassword = encoder.encode(signUpDto.getPassword());
    Member member = UserMapper.INSTANCE.toMember(signUpDto, encodedPassword);
    userDuplicationChecker.emailDuplicationChecker(signUpDto.getEmail());
    userDuplicationChecker.nickNameDuplicationChecker(signUpDto.getNickname());
    memberRepository.save(member);
  }


  @Override
  public LoginSuccessResponse login(Long id) {
    Member member = getMember(id);
    return UserMapper.INSTANCE.toLoginSuccessDto(member);

  }

  @Override
  public void update(UserUpdateDto userUpdateDto, Long id) {
    Member member = getMember(id);
    UserMapper.INSTANCE.updateMemberFromDto(userUpdateDto, member);
    memberRepository.save(member);
  }

  @Override
  public UserInfoResponse getInfo(Long userId) {

    Member member = getMember(userId);
    List<PostDto> postDtoList = member.getPosts().stream()
                .map(PostMapper.INSTANCE::toPostDto)
                .collect(Collectors.toList());
    return new UserInfoResponse(member.getProfileImg(), member.getNickname(), member.getEmail(), postDtoList);

  }

  private Member getMember(Long id) {
    return memberRepository.findById(id).orElseThrow(() -> {
      throw new UserException("존재 하지 않는 유저입니다.");
    });

  }
}
