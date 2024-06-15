package com.handsome.mall.entity.primary;

import com.handsome.mall.entity.history.ViewHistory;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_email", length = 10, nullable = false)
    private String email;

    @Column(name = "member_nickname", length = 10, nullable = false)
    private String nickname;

    @Column(name = "member_password", length = 255, nullable = false)
    private String password;

    @Column(name = "member_profile_img", length = 255)
    private String profileImg;

    @Column(name = "member_role", length = 10, nullable = false)
    private String role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ViewHistory> viewHistories;
}
