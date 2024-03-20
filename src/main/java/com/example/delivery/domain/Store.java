package com.example.delivery.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 20)
    private String workTime;

    @Column(nullable = false, length = 25)
    private String category;

    @Column(nullable = false, length = 100)
    private String address;

    private String imageUrl;

    private float storeScore;
//    private int likeCount;
    private double totalSales;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

//    @OneToMany(mappedBy = "store")
//    private List<Like> likes = new ArrayList<>();

    @Builder
    public Store(Integer id, String name, String workTime, String category, String address, String imageUrl, float storeScore, double totalSales, User user) {
        this.id = id;
        this.name = name;
        this.workTime = workTime;
        this.category = category;
        this.address = address;
        this.imageUrl = imageUrl;
        this.storeScore = storeScore;
        //this.likeCount = likeCount;
        this.totalSales = totalSales;
        this.user = user;
    }

    // 주문 완료 > 총 주문 금액을 매출에 더해주는 메서드
    public void plusSales(double orderTotalPrice) {
        this.totalSales += orderTotalPrice;
    }
}
