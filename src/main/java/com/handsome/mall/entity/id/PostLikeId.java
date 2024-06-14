package com.handsome.mall.entity.id;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class PostLikeId implements Serializable {

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "member_id")
    private Long memberId;

    @Override
    public int hashCode() {
        return Objects.hash(postId, memberId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostLikeId)) {
            return false;
        }
        PostLikeId that = (PostLikeId) o;
        return postId.equals(that.postId) && memberId.equals(that.memberId);
    }
}
