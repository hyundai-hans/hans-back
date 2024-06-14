package com.handsome.mall.entity.history;

import com.handsome.mall.entity.id.ViewHistoryId;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "view_history")
public class ViewHistory {

    @EmbeddedId
    private ViewHistoryId viewHistoryId;

    @Column(name = "view_history_count")
    private Long viewCount;

    @Column(name = "view_history_img_url", length = 255)
    private String imgUrl;

}
