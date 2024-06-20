package com.handsome.mall.repository.history;

import com.handsome.mall.entity.history.ViewHistory;
<<<<<<< Updated upstream
import com.handsome.mall.entity.id.ViewHistoryId;
import java.util.List;
import java.util.Optional;
=======
import java.util.List;
>>>>>>> Stashed changes
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewHistoryRepository extends JpaRepository<ViewHistory, Long> {

  @Query("SELECT vh FROM ViewHistory vh WHERE vh.id.memberId = :memberId")
   List<ViewHistory> getAllByIdMemberId(Long memberId);
<<<<<<< Updated upstream

  Optional<ViewHistory> findById(ViewHistoryId viewHistoryId);
=======
>>>>>>> Stashed changes
}
