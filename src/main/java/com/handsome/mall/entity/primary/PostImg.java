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
@Table(name = "post_img")
public class PostImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_img_id")
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "post_img_url", length = 255)
    private String imgUrl;

    @Column(name = "is_thumbnail")
    private Boolean isThumbnail;

}
