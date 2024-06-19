package com.handsome.mall.http.controller;


import com.handsome.mall.service.HistoryService;
import com.handsome.mall.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/users/histories")
@RestController
public class HistoryController {

  private final PostService postService;
  private final HistoryService historyService;


}
