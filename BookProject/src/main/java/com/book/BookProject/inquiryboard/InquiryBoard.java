package com.book.BookProject.inquiryboard;

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
@Table(name = "INQUIRYBOARD")
public class InquiryBoard {

    @Id // 엔티티의 주키(primary key)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가(Auto Increment)를 지정
    private  Long qidx; // PK, 인덱스
    @Column(name="QRESPONSES")
    private int responses; // 답변 여부
    @Column(name="NICK")
    private String nick; // FK, 작성자 - 닉네임
    @Column(name="QTITLE")
    private String title; // 제목
    @Column(name="QCONTENT")
    private String content; // 내용
    @Column(name="QPASS")
    private String pass; // 글 비밀번호
    @Column(name="QCREATE_DATE")
    private LocalDate createDate; // 작성일
    @LastModifiedDate
    @Column(name="QUPDATE_DATE")
    private LocalDate updateDate; // 수정일
    @Column(name="QVIEW_COUNT")
    private int viewCount; // 조회수
    @Column(name="QOFILE")
    private String ofile; // 원본 파일명
    @Column(name="QSFILE")
    private String sfile; // 저장된 파일명
    @Column(name="PARENT_QIDX")
    private Long parentIdx; // 부모 인덱스
    @Column(name="LEVEL")
    private int level; // 레벨 (게시글: 0, 답글: 1 이상)
}
