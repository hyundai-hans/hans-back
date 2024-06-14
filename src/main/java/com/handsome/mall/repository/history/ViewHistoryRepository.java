package com.handsome.mall.repository.history;

import com.handsome.mall.entity.history.ViewHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {
}
