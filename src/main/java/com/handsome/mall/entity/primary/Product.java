package com.handsome.mall.entity.primary;

import com.handsome.mall.entity.BaseEntity;
import com.handsome.mall.entity.history.ViewHistory;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.annotations.One;

@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_product_name_img_url", columnList = "product_name")
})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", length = 50, nullable = false)
    private String name;

    @Column(name = "product_price", nullable = false)
    private Integer price;

    @Column(name = "product_img_url", length = 255, nullable = false)
    private String imgUrl;

    @Column(name = "product_uri", length = 255, nullable = false)
    private String uri;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductTag> productTags;

    @OneToMany(mappedBy = "product")
    private List<Post> postList;

}
