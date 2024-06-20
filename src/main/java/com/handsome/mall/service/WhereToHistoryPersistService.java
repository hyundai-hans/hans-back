package com.handsome.mall.service;

import com.handsome.mall.dto.HistoryPostPersistDto;
import com.handsome.mall.entity.history.ViewHistory;
import java.util.List;

public interface WhereToHistoryPersistService {
    void persist(HistoryPostPersistDto dto);
    List<ViewHistory> get(Long userId);
}
