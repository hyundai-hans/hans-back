package com.handsome.mall.entity.primary;

import com.handsome.mall.entity.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "product_tag")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class ProductTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_product_id", nullable = false)
    private Product product;

    @Column(name = "tag_product_body", length = 50, nullable = false)
    private String tagBody;
}
