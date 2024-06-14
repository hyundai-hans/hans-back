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
public class ViewHistoryId implements Serializable {

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "product_id")
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ViewHistoryId)) {
            return false;
        }
        ViewHistoryId that = (ViewHistoryId) o;
        return memberId.equals(that.memberId) && productId.equals(that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}
