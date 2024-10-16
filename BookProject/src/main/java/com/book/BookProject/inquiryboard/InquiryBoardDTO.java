package com.book.BookProject.inquiryboard;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InquiryBoardDTO {
    private Long qidx;         // 인덱스
    private int responses;     // 답변여부
    private String nick;       // 작성자 닉네임
    private String title;      // 제목
    private String content;    // 내용
    private String pass;          // 글 비밀번호
    private LocalDate createDate; // 작성일
    private LocalDate updateDate; // 수정일
    private int viewCount;     // 조회 수
    private String ofile;      // 원본 파일명
    private String sfile;      // 저장된 파일명
    private Long parentIdx;     // 부모 인덱스
    private  int level;        // 레벨 (게시글: 0, 답글: 1 이상)
}
