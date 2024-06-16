package com.handsome.mall.entity.history;

import com.handsome.mall.entity.id.ViewHistoryId;
import com.handsome.mall.entity.primary.Member;
import com.handsome.mall.entity.primary.Product;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "view_history")
@NoArgsConstructor
@Getter
public class ViewHistory {

    @EmbeddedId
    private ViewHistoryId id;
    
    @Column(name = "view_history_count", nullable = false)
    private Long viewHistoryCount;

    @Column(name = "view_history_img_url", length = 255, nullable = false)
    private String viewHistoryImgUrl;
}

