package com.handsome.mall.entity.primary;

import com.handsome.mall.entity.BaseEntity;
import java.sql.Blob;
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
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "post_img")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostImg extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_img_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade ={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "post_id")
    private Post post;

    @Lob
    @Column(name = "post_img_url", nullable = false)
    private String imgUrl;

    @Column(name = "is_thumbnail", nullable = false)
    private Boolean isThumbnail;

}

