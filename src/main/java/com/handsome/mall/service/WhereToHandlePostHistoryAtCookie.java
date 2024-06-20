package com.handsome.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.handsome.mall.dto.response.PostHistoryResponse;
import com.handsome.mall.entity.primary.Post;
import com.handsome.mall.mapper.HistoryMapper;
import com.handsome.mall.repository.primary.PostRepository;
import com.handsome.mall.util.CookieUtil;
import com.handsome.mall.valueobject.HistoryType;
<<<<<<< Updated upstream
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
=======
>>>>>>> Stashed changes
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WhereToHandlePostHistoryAtCookie implements
    WhereToHandlePostHistoryService {

  private final PostRepository postRepository;
  private final HttpServletResponse httpServletResponse;
  @Value("${cookie.application.domain}")
  private String domain;
  @Value("${cookie.application.age}")
  private int cookieAge;

  @Override
  public List<PostHistoryResponse> handle(List<Long> productIdList) throws JsonProcessingException {
    List<Post> posts = postRepository.findByProductIdIn(productIdList);

    List<PostHistoryResponse> result = posts.stream()
        .map(HistoryMapper.INSTANCE::toPostHistoryResponse)
        .collect(Collectors.toList());

<<<<<<< Updated upstream
      String convertedToJson = getJsonString(result);
    String encodedJson = URLEncoder.encode(convertedToJson, StandardCharsets.UTF_8);
    Cookie bannerCookie = CookieUtil.createCookie(HistoryType.BANNER.getValue(),
        encodedJson, cookieAge, domain);
=======
    Cookie bannerCookie = CookieUtil.createCookie(HistoryType.BANNER.getValue(),
        getJsonString(result), cookieAge, domain);
>>>>>>> Stashed changes
    httpServletResponse.addCookie(bannerCookie);

    return result;

  }

  private String getJsonString(List<PostHistoryResponse> result) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
<<<<<<< Updated upstream
    return objectMapper.writeValueAsString(result);
=======
    String listAsJson = objectMapper.writeValueAsString(result);
    return listAsJson;
>>>>>>> Stashed changes

  }
}
