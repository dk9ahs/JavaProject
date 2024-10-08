package com.book.BookProject.sales;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "SALESBOARD")
public class SalesBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sidx;  // Primary Key

//    @ManyToOne
//    @JoinColumn(name = "nick")  // 외래키 설정
//    private UserEntity Nick;  // 회원 정보 (닉네임을 포함하는 Member 테이블 연관)

    private String nick; // 더미

    @Column(name = "sbooktitle", nullable = false)
    private String sbooktitle;  // 책 제목

    @Lob
    @Column(nullable = false)
    private String scontent;  // 내용

    @Column(updatable = false)
    private LocalDateTime screatedate = LocalDateTime.now();  // 생성 시간

    private LocalDateTime supdatedate;  // 수정 시간

    @PreUpdate
    public void setLastUpdate() {
        this.supdatedate = LocalDateTime.now();
    }

    @Column(nullable = false)
    private int sViewCount = 0;  // 조회수 기본값 0

    @Column(nullable = false)
    private int sLikeCount = 0;  // 좋아요 수 기본값 0

    private String oImage;  // 외부 이미지 경로

    private String sImage;  // 내부 이미지 경로

    @Column(nullable = false)
    private String price;  // 가격

}