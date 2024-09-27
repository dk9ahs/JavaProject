//package com.book.BookProject.member;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "MEMBER")  // 테이블 이름을 대문자로 설정
//public class UserEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 기본 키 자동 생성
//    private Long id;               // 기본 키 (자동 생성)
//
//    @Column(nullable = false, unique = true)
//    private String nick;           // 고유 닉네임
//
//    @Column(nullable = false)
//    private String pwd;            // 비밀번호
//
//    @Column(nullable = false)
//    private String name;           // 이름
//
//    @Column(nullable = false)
//    private String phone;          // 전화번호
//
//    @Column(nullable = false, unique = true)
//    private String email;          // 이메일
//
//    private String zipcode;        // 우편번호
//
//    private String address;        // 주소
//
//    private String detailAddress;  // 상세 주소
//
//    private LocalDateTime createDate;  // 생성 날짜
//
//    private LocalDateTime updateDate;  // 수정 날짜
//
//    private String authority;      // 권한 (e.g., ROLE_USER)
//
//    private int enabled;           // 활성화 여부
//
//    private String socialId;       // 소셜 아이디
//
//    private String socialProvider; // 소셜 제공자
//
//    private String socialEmail;    // 소셜 이메일
//
//    private int failedAttempts;    // 실패 시도 횟수
//
//    private int accountLocked;     // 계정 잠금 여부
//}
