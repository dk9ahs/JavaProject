package com.book.BookProject.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor를 포함
@NoArgsConstructor //파라미터가 없는 기본 생성자를 생성
@AllArgsConstructor //모든 필드를 인자로 받는 생성자를 생성
@Builder // 빌더 패턴 적용
public class UserDTO {

    private String id;               // 기본 키
    private String nick;             // 고유 닉네임
    private String pwd;              // 비밀번호
    private String name;             // 이름
    private String phone;            // 전화번호
    private String email;            // 이메일
    private String zipcode;          // 우편번호
    private String address;          // 주소
    private String detailAddress;    // 상세 주소
    private LocalDateTime createDate; // 생성 날짜
    private LocalDateTime updateDate; // 수정 날짜
    private String authority;        // 권한
    private int enabled;             // 활성화 여부
    private String socialId;         // 소셜 아이디
    private String socialProvider;   // 소셜 제공자
    private String socialEmail;      // 소셜 이메일
    private int failedAttempts;      // 실패 시도 횟수
    private int accountLocked;       // 계정 잠금 여부
}