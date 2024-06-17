package com.handsome.mall.entity.history;

import com.handsome.mall.entity.BaseEntity;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Table(name = "view_history")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ViewHistory extends BaseEntity {

    @EmbeddedId
    private ViewHistoryId id;
    
    @Column(name = "view_history_count", nullable = false)
    private Long viewHistoryCount;

    @Column(name = "view_history_img_url", length = 255, nullable = false)
    private String viewHistoryImgUrl;
}

