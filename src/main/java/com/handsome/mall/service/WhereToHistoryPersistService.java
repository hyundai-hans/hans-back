package com.handsome.mall.service;

<<<<<<< Updated upstream
import com.handsome.mall.dto.HistoryPostPersistDto;
=======
import com.handsome.mall.dto.HistoryPersistDto;
>>>>>>> Stashed changes
import com.handsome.mall.entity.history.ViewHistory;
import java.util.List;

public interface WhereToHistoryPersistService {
<<<<<<< Updated upstream
    void persist(HistoryPostPersistDto dto);
=======
    void persist(HistoryPersistDto dto);
>>>>>>> Stashed changes
    List<ViewHistory> get(Long userId);
}
