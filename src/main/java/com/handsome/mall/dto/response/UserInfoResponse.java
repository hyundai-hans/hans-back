package com.handsome.mall.dto.response;

import com.handsome.mall.dto.PostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserInfoResponse {

  private String profileImg;
  private String nickName;
  private String email;
  private List<PostDto> postList;

}
