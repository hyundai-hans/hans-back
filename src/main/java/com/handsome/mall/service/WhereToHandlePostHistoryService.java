package com.handsome.mall.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.handsome.mall.dto.response.PostHistoryResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface WhereToHandlePostHistoryService {
   List<PostHistoryResponse> handle(List<Long> productIdList) throws JsonProcessingException;
}
