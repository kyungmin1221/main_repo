package com.example.delivery.domain;

import com.example.delivery.constant.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private long point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private Long kakaoId;

    @Builder
    public User(String email, String password , String nickname, long point, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.point = point;
        this.role = role;
    }


    public User(String email, String nickname, String encodedPassword, long point, Role role, Long kakaoId) {
        this.email = email;
        this.nickname = nickname;
        this.password = encodedPassword;
        this.point = point;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    // 사장님 - 음식점 배달 완료시 포인트 지급
    public void plusPoint(double orderTotalPrice) {
        this.point += (long) orderTotalPrice;
    }
}
