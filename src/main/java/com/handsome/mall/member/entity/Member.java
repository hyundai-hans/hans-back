package com.handsome.mall.member.entity;

import lombok.Data;

@Data
public class Member {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private String profileImg;
    private String role;
}