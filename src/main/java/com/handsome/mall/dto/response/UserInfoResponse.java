package com.handsome.mall.dto.response;

import com.handsome.mall.dto.PostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserInfoResponse {

  private String profileImg;
  private String nickname;
  private String email;
  private List<PostDto> postList;

}
