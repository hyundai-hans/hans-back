package com.handsome.mall.entity.history;

import com.handsome.mall.entity.BaseEntity;
import com.handsome.mall.entity.id.ViewHistoryId;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Table(name = "view_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ViewHistory extends BaseEntity {

    @EmbeddedId
    private ViewHistoryId id;
    
    @Column(name = "view_history_read_count", nullable = false)
    private Long readCount;

    @Lob
    @Column(name = "view_history_thumbmnai_imag_url", nullable = false)
    private String thumbnailImagUrl;
}

