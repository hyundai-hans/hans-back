package com.handsome.mall.entity.primary;

import com.handsome.mall.entity.id.PostLikeId;
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
@Table(name = "post_like")
public class PostLike {

    @EmbeddedId
    private PostLikeId id;

    @Column(name = "post_is_liked")
    private Boolean isLiked;

}
