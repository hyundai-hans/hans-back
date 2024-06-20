package com.handsome.mall.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
<<<<<<< Updated upstream
import com.handsome.mall.dto.HistoryPostPersistDto;
=======
>>>>>>> Stashed changes
import com.handsome.mall.dto.response.PostHistoryResponse;
import com.handsome.mall.entity.history.ViewHistory;
import com.handsome.mall.service.WhereToHandlePostHistoryService;
import com.handsome.mall.service.WhereToHistoryPersistService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
<<<<<<< Updated upstream
import org.springframework.transaction.annotation.Transactional;
=======
>>>>>>> Stashed changes

@Component
@RequiredArgsConstructor
public class PostHistoryManageHandler {

    private final WhereToHandlePostHistoryService handleHistoryService;
    private final WhereToHistoryPersistService historyPersistService;

<<<<<<< Updated upstream
    @Transactional("primaryTransactionManager")
=======
>>>>>>> Stashed changes
    public List<PostHistoryResponse> handle(Long userId) throws JsonProcessingException {
        return handleHistoryService.handle(getProductListFromHistory(userId));
    }

<<<<<<< Updated upstream
    public void persistHistory(HistoryPostPersistDto dto){
        historyPersistService.persist(dto);
    }

    private List<Long> getProductListFromHistory(Long userId) {
        List<ViewHistory> historyList = historyPersistService.get(userId);
        return historyList.stream().map(history -> history.getId().getPostId())
=======
    private List<Long> getProductListFromHistory(Long userId) {
        List<ViewHistory> historyList = historyPersistService.get(userId);
        return historyList.stream().map(history -> history.getId().getProductId())
>>>>>>> Stashed changes
            .collect(Collectors.toList());
    }
}
