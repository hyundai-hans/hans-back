package com.handsome.mall.entity;

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
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", length = 10)
    private String email;

    @Column(name = "member_nickname", length = 10)
    private String nickname;

    @Column(name = "member_password", length = 255)
    private String password;

    @Column(name = "member_profile_img", length = 255)
    private String profileImg;

    @Column(name = "member_role", length = 10)
    private String role;

}
