package com.book.BookProject.inquiryboard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InquiryBoardDTO {
    private Long qidx;         // 인덱스

    private Long originNo; // 그룹 번호 (원글 번호)    private String parentId;    // 부모 ID
    private Integer groupOrd; // 그룹 내 순서
    private Integer groupLayer; // 그룹 계층 (답글의 깊이)
    private Integer responses;     // 응답 수

    private String nick;       // 작성자 닉네임
    private String title;      // 제목
    private String content;    // 내용
    private String pass;          // 글 비밀번호
    private LocalDate createDate; // 생성 날짜
    private LocalDate updateDate; // 업데이트 날짜
    private Integer viewCount;     // 조회 수
    private Integer likeCount;     // 좋아요 수
    private String ofile;      // 원본 파일명
    private String sfile;      // 저장된 파일명
}
