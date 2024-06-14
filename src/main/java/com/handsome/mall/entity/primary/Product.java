package com.handsome.mall.entity.primary;

import javax.persistence.Column;
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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", length = 50)
    private String name;

    @Column(name = "product_price")
    private Long price;

    @Column(name = "product_img_url", length = 255)
    private String imgUrl;

    @Column(name = "product_url", length = 255)
    private String url;

    // Getters and Setters
}
