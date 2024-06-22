package com.handsome.mall.service;

import com.handsome.mall.dto.response.PostHistoryResponse;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.mapper.HistoryMapper;
import com.handsome.mall.repository.primary.PostRepository;
import com.handsome.mall.util.CookieUtil;
import com.handsome.mall.valueobject.HistoryType;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HandlePostHistoryAtCookie implements
    WhereToHandlePostHistoryService {

  private final PostRepository postRepository;
  private final HttpServletResponse httpServletResponse;
  @Value("${cookie.application.domain}")
  private String domain;
  @Value("${cookie.application.age}")
  private int cookieAge;


  private String combineIntoIdString(List<String> postIdList) {
    StringBuilder sb = new StringBuilder();
    for (String s : postIdList) {
      sb.append(s);
      sb.append(":");
    }
    return sb.toString();
  }


  @Override
  public List<PostHistoryResponse> handle(List<Long> postIdList)  {
    List<Post> posts = postRepository.findByIdIn(postIdList);
    List<String> idList = posts.stream().map(post -> String.valueOf(post.getId()))
        .collect(Collectors.toList());
    List<PostHistoryResponse> result = posts.stream()
        .map(HistoryMapper.INSTANCE::toPostHistoryResponse)
        .collect(Collectors.toList());

    Cookie bannerCookie = CookieUtil.createCookie(HistoryType.BANNER.getValue(),
        combineIntoIdString(idList), cookieAge, domain);
    httpServletResponse.addCookie(bannerCookie);
    return result;
  }

}
