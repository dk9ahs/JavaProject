package com.book.BookProject.inquiryreply;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "INQUIRYREPLY")
public class InquiryReply {

    @Id // 엔티티의 주키(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가(Auto Increment)를 지정
    private  Long idx; // PK, 인덱스
    @Column(name="PARENTIDX")
    private int parentIdx; // FK, FK, 부모인덱스(문의게시판 인덱스)
    @Column(name="NICK")
    private String nick; // 닉네임
    @Column(name="TITLE")
    private String title; // 제목
    @Column(name="CONTENT")
    private String content; // 내용
    @Column(name="PASS")
    private String pass; // 글 비밀번호
    @Column(name="CREATE_DATE")
    private LocalDate createDate; // 작성일
    @LastModifiedDate
    @Column(name="UPDATE_DATE")
    private LocalDate updateDate; // 수정일
    @Column(name="VIEW_COUNT")
    private int viewCount; // 조회수
    @Column(name="OFILE")
    private String ofile; // 원본 파일명
    @Column(name="SFILE")
    private String sfile; // 저장된 파일명
}
