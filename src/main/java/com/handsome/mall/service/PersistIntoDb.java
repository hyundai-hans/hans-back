package com.handsome.mall.service;

import com.handsome.mall.dto.HistoryPostPersistDto;
import com.handsome.mall.entity.history.ViewHistory;
import com.handsome.mall.entity.id.ViewHistoryId;
import com.handsome.mall.mapper.HistoryMapper;
import com.handsome.mall.repository.history.ViewHistoryRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersistIntoDb implements WhereToHistoryPersistService {

    private final ViewHistoryRepository historyRepository;


    @Override
    public void persist(HistoryPostPersistDto dto) {

        Long viewCounter = 0L;
        ViewHistoryId viewHistoryId = ViewHistoryId.builder().memberId(
            dto.getMemberId()).postId(dto.getPostId()).build();

        Optional<ViewHistory> viewHistoryOptional= historyRepository.findById(viewHistoryId);

        if(viewHistoryOptional.isPresent()){
            viewCounter = viewHistoryOptional.get().getReadCount();
        }
        ViewHistory viewHistory = HistoryMapper.INSTANCE.toEntity(dto,viewCounter);
        historyRepository.save(viewHistory);
    }

    @Override
    public List<ViewHistory> get(Long userId) {
        return historyRepository.getAllByIdMemberId(userId);
    }
}
