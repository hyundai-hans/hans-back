package com.handsome.mall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.handsome.mall.dto.HistoryPostPersistDto;
import com.handsome.mall.dto.response.PostHistoryResponse;
import com.handsome.mall.entity.history.ViewHistory;
import com.handsome.mall.service.WhereToHandlePostHistoryService;
import com.handsome.mall.service.WhereToHistoryPersistService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class PostHistoryManageHandler {

    private final WhereToHandlePostHistoryService handleHistoryService;
    private final WhereToHistoryPersistService historyPersistService;

    @Transactional("primaryTransactionManager")
    public List<PostHistoryResponse> handle(Long userId) throws JsonProcessingException {
        return handleHistoryService.handle(getProductListFromHistory(userId));
    }

    public void persistHistory(HistoryPostPersistDto dto){
        historyPersistService.persist(dto);
    }

    private List<Long> getProductListFromHistory(Long userId) {
        List<ViewHistory> historyList = historyPersistService.get(userId);
        return historyList.stream().map(history -> history.getId().getPostId())
            .collect(Collectors.toList());
    }
}
